package net.thumbtack.onlineshop.service;


import net.thumbtack.onlineshop.daoimpl.BasketDaoImpl;
import net.thumbtack.onlineshop.daoimpl.ClientDaoImpl;
import net.thumbtack.onlineshop.daoimpl.ProductDaoImpl;
import net.thumbtack.onlineshop.dto.request.ProductForPurchaseDtoRequest;
import net.thumbtack.onlineshop.dto.response.EmptyResponse;
import net.thumbtack.onlineshop.dto.response.ProductDtoResponse;
import net.thumbtack.onlineshop.dto.response.ProductsInBasketDtoResponse;
import net.thumbtack.onlineshop.exception.AppException;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.exception.ServiceException;
import net.thumbtack.onlineshop.model.Basket;
import net.thumbtack.onlineshop.model.Client;
import net.thumbtack.onlineshop.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

import static net.thumbtack.onlineshop.exception.AppErrorCode.*;

@Service
public class BasketService extends BaseService {

    private BasketDaoImpl basketDao;
    private ClientDaoImpl clientDao;
    private ProductDaoImpl productDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(BasketService.class);

    @Autowired
    public BasketService(BasketDaoImpl basketDao, ClientDaoImpl clientDao, ProductDaoImpl productDao) {
        this.basketDao = basketDao;
        this.clientDao = clientDao;
        this.productDao = productDao;
    }

    public ProductsInBasketDtoResponse addProductToBasket(ProductForPurchaseDtoRequest productDtoRequest, String token) {
        LOGGER.debug("Service add product to basket Product {}", productDtoRequest);
        try {
            Client client = clientDao.getClientByToken(token);
            checkIsClient(client);

            Product storageProduct = getProductById(productDtoRequest.getId());
            Product currentProduct = new Product(productDtoRequest.getId(), productDtoRequest.getName(), productDtoRequest.getPrice(), productDtoRequest.getCount() == 0 ? 1 : productDtoRequest.getCount());

            if (productValidForBasket(storageProduct, currentProduct)) {
                client.getBasket().setProducts(Collections.singletonList(basketDao.addProductToBasket(client, currentProduct)));
            }

            Basket basket = basketDao.getAllProductsFromBasket(client);

            return new ProductsInBasketDtoResponse(basket.getProducts().stream()
                    .map(prod -> new ProductDtoResponse(prod.getId(), prod.getName(), prod.getPrice(), prod.getCount()))
                    .collect(Collectors.toList()));

        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    private Product getProductById(int id) {
        try {
            return productDao.getProductById(id);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public EmptyResponse deleteProductFromBasket(int productId, String token) {
        LOGGER.debug("Service delete product from basket by Id", productId);
        try {
            validClient(token);

            basketDao.deleteProductFromBasket(productId);

            return new EmptyResponse();
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public ProductsInBasketDtoResponse updateProductCountInBasket(String token, ProductForPurchaseDtoRequest productDtoRequest) {
        LOGGER.debug("Service update product in basket Product", productDtoRequest);
        try {
            Client client = clientDao.getClientByToken(token);
            checkIsClient(client);

            Product productForUpdate = new Product(productDtoRequest.getId(), productDtoRequest.getName(), productDtoRequest.getPrice(), productDtoRequest.getCount());

            changeProductCountInBasket(productForUpdate, client);

            Basket basket = basketDao.getAllProductsFromBasket(client);

            return new ProductsInBasketDtoResponse(basket.getProducts().stream()
                    .map(prod -> new ProductDtoResponse(prod.getId(), prod.getName(), prod.getPrice(), prod.getCount()))
                    .collect(Collectors.toList()));

        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    private void changeProductCountInBasket(Product productForUpdate, Client client) {
        client.getBasket().getProducts().stream()
                .filter(productInBasket -> productInBasket.getId() == productForUpdate.getId())
                .forEach(productInBasket -> {
                    if (productInBasket == null) {
                        throw new ServiceException(SUCH_PRODUCT_NO_IN_BASKET);
                    } else if (productValidForBasket(productInBasket, productForUpdate)) {
                        productInBasket.setCount(productForUpdate.getCount());
                        basketDao.updateProductInBasket(productInBasket);
                    }
                });
    }

    public ProductsInBasketDtoResponse getBasketContent(String token) {
        LOGGER.debug("Service get content in basket by token {}", token);
        try {
            Client client = clientDao.getClientByToken(token);
            checkIsClient(client);

            return new ProductsInBasketDtoResponse(client.getBasket().getProducts().stream()
                    .map(prod -> new ProductDtoResponse(prod.getId(), prod.getName(), prod.getPrice(), prod.getCount()))
                    .collect(Collectors.toList()));
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    private boolean productValidForBasket(Product productInBasket, Product productForUpdate) {
        AppException appException = new AppException();
        if (productInBasket == null) {
            appException.addException(new ServiceException(PRODUCT_HAS_BEEN_DELETED));
        }
        if (!appException.getServiceExceptions().isEmpty()) {
            throw appException;
        }
        return productNameAndPriceValid(productInBasket, productForUpdate, appException);
    }

    protected boolean productNameAndPriceValid(Product productInBasket, Product productForUpdate, AppException appException) {
        if (!productInBasket.getName().equals(productForUpdate.getName())) {
            appException.addException(new ServiceException(WRONG_NAME));
        }
        if (productInBasket.getPrice() != productForUpdate.getPrice()) {
            appException.addException(new ServiceException(WRONG_PRODUCT_PRICE));
        }
        if (!appException.getServiceExceptions().isEmpty()) {
            throw appException;
        }
        return true;
    }

    private boolean thisProductIsInBasket(Product productForUpdate, Product productInBasket) {
        return productInBasket.getId() == productForUpdate.getId();
    }
}

