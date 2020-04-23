package net.thumbtack.onlineshop.daoimpl;

import net.thumbtack.onlineshop.dao.PurchaseDao;
import net.thumbtack.onlineshop.exception.AppErrorCode;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.model.Client;
import net.thumbtack.onlineshop.model.Deposit;
import net.thumbtack.onlineshop.model.Product;
import net.thumbtack.onlineshop.model.Purchase;
import net.thumbtack.onlineshop.mybatis.mappers.DepositMapper;
import net.thumbtack.onlineshop.mybatis.mappers.ProductMapper;
import net.thumbtack.onlineshop.mybatis.mappers.PurchaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static net.thumbtack.onlineshop.exception.AppErrorCode.WRONG_VERSION_FOR_UPDATE;

@Component
public class PurchaseDaoImpl implements PurchaseDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseDaoImpl.class);
    private ProductMapper productMapper;
    private DepositMapper depositMapper;
    private PurchaseMapper purchaseMapper;

    @Autowired
    public PurchaseDaoImpl(ProductMapper productMapper, DepositMapper depositMapper, PurchaseMapper purchaseMapper) {
        this.productMapper = productMapper;
        this.depositMapper = depositMapper;
        this.purchaseMapper = purchaseMapper;
    }

    @Transactional
    @Override
    public Product purchaseProduct(Client client, Purchase purchase, Product currentProduct, Product onStorageProduct) {
        LOGGER.debug("DAO purchase product Product {}", currentProduct);
        try {
            makePurchaseProduct(client, purchase, currentProduct);
            updateClientDeposit(client, client.getDeposit());
            onStorageProduct.setCount(onStorageProduct.getCount() - currentProduct.getCount());
            updateProductCountOnStorage(onStorageProduct);
            return currentProduct;
        } catch (RuntimeException ex) {
            LOGGER.error("Can't purchase product Product {} {}", currentProduct, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    @Transactional
    @Override
    public List<Product> purchaseProductsFromBasket(Client client, Purchase purchase) {
        try {
            List<Product> products = purchase.getProducts();
            List<Product> productsFromStorage = productMapper.getProductsByListId(products.stream().map(product -> product.getId()).collect(Collectors.toList()));
            updateListProductCountOnStorage(productsFromStorage);
            checkSuccessPutDeposit(depositMapper.putDeposit(client.getId(), client.getDeposit()));
            return products;
        } catch (RuntimeException ex) {
            LOGGER.error("Can't purchase product Product {} {}", purchase, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    private void checkSuccessPutDeposit(int resultUpdate) {
        if (resultUpdate != 1) {
            throw new DaoException(WRONG_VERSION_FOR_UPDATE);
        }
    }

    private void updateListProductCountOnStorage(List<Product> products) {
        products.forEach(this::updateProductCountOnStorage);
    }


    @Override
    public List<Purchase> getProfitByDateRange(Date startDare, Date endDate, Integer limit, Integer offset, String criterion) {
        LOGGER.debug("DAO get profit by date range Date{}, Date{}", startDare, endDate);
        try {
            return purchaseMapper.getProfitByDateRange(startDare, endDate, limit, offset, criterion);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get profit by date range Date{}, Date{}", startDare, endDate, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    private void makePurchaseProduct(Client client, Purchase purchase, Product currentProduct) {
        purchaseMapper.addPurchase(client.getId(), purchase);
        purchaseMapper.addPurchaseProduct(purchase, currentProduct);
    }

    private void updateClientDeposit(Client client, Deposit deposit) {
        checkSuccessPutDeposit(depositMapper.putDeposit(client.getId(), deposit));
    }

    private void updateProductCountOnStorage(Product onStorageProduct) {
        checkSuccessUpdateProductCount(productMapper.updateProductCount(onStorageProduct));
    }

    private void checkSuccessUpdateProductCount(int resultUpdate) {
        if (resultUpdate != 1) {
            throw new DaoException(WRONG_VERSION_FOR_UPDATE);
        }
    }
}

