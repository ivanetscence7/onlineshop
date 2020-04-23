package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.dao.ProductDao;
import net.thumbtack.onlineshop.daoimpl.ClientDaoImpl;
import net.thumbtack.onlineshop.daoimpl.PurchaseDaoImpl;
import net.thumbtack.onlineshop.dto.request.ProductForPurchaseDtoRequest;
import net.thumbtack.onlineshop.dto.response.*;
import net.thumbtack.onlineshop.dto.response.StatementDtoResponse;
import net.thumbtack.onlineshop.exception.AppException;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.exception.ServiceException;
import net.thumbtack.onlineshop.model.Category;
import net.thumbtack.onlineshop.model.Client;
import net.thumbtack.onlineshop.model.Product;
import net.thumbtack.onlineshop.model.Purchase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static net.thumbtack.onlineshop.exception.AppErrorCode.*;

@Service
public class PurchaseService extends BaseService {

    private PurchaseDaoImpl purchasesDao;
    private ClientDaoImpl clientDao;
    private ProductDao productDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseService.class);

    @Autowired
    public PurchaseService(PurchaseDaoImpl purchasesDao, ClientDaoImpl clientDao, ProductDao productDao) {
        this.purchasesDao = purchasesDao;
        this.clientDao = clientDao;
        this.productDao = productDao;
    }

    public ProductDtoResponse purchaseProduct(ProductForPurchaseDtoRequest purchaseDtoRequest, String token) throws AppException {
        LOGGER.debug("Service purchase Product {}", purchaseDtoRequest);
        try {
            Client client = (Client) getUserByToken(token);
            checkIsClient(client);

            Product onStorageProduct = getProductById(purchaseDtoRequest.getId());
            Product currentProduct = new Product(purchaseDtoRequest.getId(), purchaseDtoRequest.getName(), purchaseDtoRequest.getPrice(), purchaseDtoRequest.getCount() == 0 ? 1 : purchaseDtoRequest.getCount());

            if (productCanBePurchase(client, onStorageProduct, currentProduct)) {
                Purchase purchase = new Purchase(new Date(), currentProduct.getPrice() * currentProduct.getCount());
                client.getDeposit().setAmount(client.getDeposit().getAmount() - purchase.getAmount());
                currentProduct = purchasesDao.purchaseProduct(client, purchase, currentProduct, onStorageProduct);
            }
            return new ProductDtoResponse(currentProduct.getId(), currentProduct.getName(), currentProduct.getPrice(), currentProduct.getCount());

        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    private Product getProductById(int productId) {
        try {
            return productDao.getProductById(productId);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public PurchasesFromBasketDtoResponse purchaseProductsFromBasket(List<ProductForPurchaseDtoRequest> purchasesDtoRequests, String token) {
        LOGGER.debug("Service purchase products in basket {}", purchasesDtoRequests);
        try {
            Client client = clientDao.getClientByToken(token);
            checkIsClient(client);
            List<Product> productsInBasket = client.getBasket().getProducts();

            final List<Product> prebought = new ArrayList<>();
            List<ProductDtoResponse> remaining = new ArrayList<>();
            List<ProductDtoResponse> bought = new ArrayList<>();

            purchasesDtoRequests.stream().filter(reqProduct -> isValidProduct(reqProduct, productsInBasket))
                    .forEach(reqProduct -> prebought.add(new Product(reqProduct.getId(), reqProduct.getName(), reqProduct.getPrice(), reqProduct.getCount())));

            purchasesDtoRequests.stream().filter(c -> !isValidProduct(c, productsInBasket))
                    .forEach(r -> remaining.add(new ProductDtoResponse(r.getId(), r.getName(), r.getPrice(), r.getCount())));

            validSumForPurchases(prebought, client.getDeposit().getAmount());

            client.getDeposit().setAmount(client.getDeposit().getAmount() - prebought.stream().mapToInt((a) -> a.getCount() * a.getPrice()).sum());

            List<Product> finalbought = purchasesDao.purchaseProductsFromBasket(client, new Purchase(new Date(), getSumAmount(prebought), prebought));
            finalbought.forEach(product -> bought.add(new ProductDtoResponse(product.getId(), product.getName(), product.getPrice(), product.getCount())));

            return new PurchasesFromBasketDtoResponse(bought, remaining);

        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public StatementDtoResponse getStatementByParams(List<Integer> category, List<Integer> product, List<Integer> clientId,
                                                     Integer limit, Integer offset, Date startDate, Date endDate, String criterion, String token) {
        LOGGER.debug("Service get statement by params {}, {}, {} ,{}, {}, {}, {}, {}", category, product, clientId, limit, offset, startDate, endDate, criterion);
        try {
            validAdmin(token);

            if (category != null) {
                return categoriesByListId(category);
            }
            if (product != null) {
                return productsWithCategories(product);
            }
            if (startDate != null && endDate != null) {
                return profitByDateRange(startDate, endDate, limit, offset, criterion);
            }
            if (clientId != null) {
                return clientsByListIdWithPurchases(clientId);
            }
            throw new ServiceException(INCORRECT_PARAMS);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    private int getSumAmount(List<Product> prebought) {
        return prebought.stream().mapToInt((a) -> a.getCount() * a.getPrice()).sum();
    }

    private boolean isValidProduct(ProductForPurchaseDtoRequest requestProduct, List<Product> productsInBasket) {
        Product currentProduct = new Product(requestProduct.getId(), requestProduct.getName(), requestProduct.getPrice(), requestProduct.getCount());
        Product storageProduct = productDao.getProductById(requestProduct.getId());
        if (!validProductFieldsForBye(storageProduct, currentProduct)) {
            return false;
        }
        for (Product product : productsInBasket) {
            if (currentProduct.getCount() == 0) {
                currentProduct.setCount(product.getCount());
            }
            if (currentProduct.getCount() > product.getCount()) {
                currentProduct.setCount(product.getCount());
            }
        }
        return true;
    }

    public boolean validProductFieldsForBye(Product onStorageProduct, Product currentProduct) {
        if (onStorageProduct == null) {
            return false;
        }
        if (!onStorageProduct.getName().equals(currentProduct.getName())) {
            return false;
        }
        if (onStorageProduct.getPrice() != currentProduct.getPrice()) {
            return false;
        }
        if (onStorageProduct.getCount() < currentProduct.getCount()) {
            return false;
        }
        return true;
    }

    private AllProductsDtoResponse productsWithCategories(List<Integer> productList) {
        List<Product> products = productDao.getProductsByListId(productList);

        return new AllProductsDtoResponse(products.stream()
                .map(product -> {
                    return new ProductWithCategoryNameDtoResponse(product.getId(), product.getName(), product.getPrice(), product.getCount(),
                            Collections.singletonList(product.getCategories().stream()
                                    .map(Category::getName).collect(Collectors.joining(",")))
                    );
                }).collect(Collectors.toList()));
    }

    private AllCategoryDtoResponse categoriesByListId(List<Integer> category) {
        List<Category> categories = getCategoriesByListId(category);

        List<Category> sortedCategoryByName = sortedCategoryAndSubCategoryByName(categories);

        return new AllCategoryDtoResponse(sortedCategoryByName.stream()
                .map(categoryInStream -> {
                            if (categoryInStream.getParent() == null) {
                                return new CategoryDtoResponse(categoryInStream.getId(), categoryInStream.getName(),
                                        categoryInStream.getProducts().stream()
                                                .map(pr -> new ProductDtoResponse(pr.getId(), pr.getName(), pr.getPrice(), pr.getCount()))
                                                .collect(Collectors.toList())
                                );
                            } else {
                                return new CategoryDtoResponse(categoryInStream.getId(), categoryInStream.getName(),
                                        categoryInStream.getParent().getId(), categoryInStream.getParent().getName(),
                                        categoryInStream.getProducts().stream()
                                                .map(pr -> new ProductDtoResponse(pr.getId(), pr.getName(), pr.getPrice(), pr.getCount()))
                                                .collect(Collectors.toList())
                                );
                            }
                        }
                ).collect(Collectors.toList()));
    }

    private AllPurchasesDtoResponse profitByDateRange(Date startDate, Date endDate, Integer limit, Integer offset, String criterion) {
        List<Purchase> purchases = purchasesDao.getProfitByDateRange(startDate, endDate, limit, offset, criterion);
        return new AllPurchasesDtoResponse(purchases.stream().mapToInt(Purchase::getAmount).sum(),
                purchases.stream().map(p -> new PurchaseDtoResponse(p.getId(), p.getDate(), p.getAmount())).collect(Collectors.toList()));
    }

    private StatementDtoResponse clientsByListIdWithPurchases(List<Integer> clientId) {
        List<Client> clients = getClientsByListId(clientId);

        return new StatementDtoResponseItem(
                clients.stream()
                        .map(c -> new InfoAboutClientDtoResponse(c.getId(), c.getFirstName(), c.getLastName(), c.getPatronymic(), c.getEmail(), c.getAddress(), c.getPhone(), c.getUserType(), c.getPurchases().stream()
                                .map(p -> new PurchaseDtoResponse(p.getId(), p.getDate(), p.getAmount())).collect(Collectors.toList()))).collect(Collectors.toList()));
    }

    private List<Client> getClientsByListId(List<Integer> clientsId) {
        List<Client> clients = new ArrayList<>();
        for (Integer clientId : clientsId) {
            clients.add(clientDao.getClientsWithPurchasesById(clientId));
        }
        return clients;
    }

    protected boolean productCanBePurchase(Client client, Product onStorageProduct, Product currentProduct) throws AppException {
        return productValid(onStorageProduct, currentProduct) && depositLetBye(client.getDeposit().getAmount(), currentProduct);
    }

    protected boolean productValid(Product onStorageProduct, Product currentProduct) throws AppException {
        AppException appException = new AppException();
        if (onStorageProduct != null && productFieldsForPurchaseValid(onStorageProduct, currentProduct, appException)) {
            return true;
        } else {
            appException.addException(new ServiceException(PRODUCT_HAS_BEEN_DELETED));
        }
        if (!appException.getServiceExceptions().isEmpty()) {
            throw appException;
        }
        return true;
    }

    protected boolean productFieldsForPurchaseValid(Product onStorageProduct, Product currentProduct, AppException appException) {
        if (!onStorageProduct.getName().equals(currentProduct.getName())) {
            appException.addException(new ServiceException(WRONG_NAME));
        }
        if (onStorageProduct.getPrice() != currentProduct.getPrice()) {
            appException.addException(new ServiceException(WRONG_PRODUCT_PRICE));
        }
        if (onStorageProduct.getCount() < currentProduct.getCount()) {
            appException.addException(new ServiceException(WRONG_PRODUCT_COUNT));
        }
        if (!appException.getServiceExceptions().isEmpty()) {
            throw appException;
        }
        return true;
    }

    protected boolean depositLetBye(int amount, Product product) {
        if (amount < product.getPrice() * product.getCount()) {
            throw new ServiceException(NOT_ENOUGH_MONEY);
        }
        return true;
    }


    protected void validSumForPurchases(List<Product> prebought, int amount) {
        if (amount < prebought.stream().mapToInt((a) -> a.getCount() * a.getPrice()).sum()) {
            throw new ServiceException(NOT_ENOUGH_MONEY);
        }
    }
}
