package net.thumbtack.onlineshop.daoimpl;

import net.thumbtack.onlineshop.dao.BasketDao;
import net.thumbtack.onlineshop.exception.AppErrorCode;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.model.Basket;
import net.thumbtack.onlineshop.model.Client;
import net.thumbtack.onlineshop.model.Product;
import net.thumbtack.onlineshop.mybatis.mappers.BasketMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BasketDaoImpl implements BasketDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasketDaoImpl.class);
    private BasketMapper basketMapper;

    @Autowired
    public BasketDaoImpl(BasketMapper basketMapper) {
        this.basketMapper = basketMapper;
    }

    @Transactional
    @Override
    public Product addProductToBasket(Client client, Product currentProduct) {
        LOGGER.debug("DAO add product to basket Product {}", currentProduct);
        try {
            Product resultProduct = basketMapper.getProductFromBasket_Product(currentProduct.getId());

            if (resultProduct == null) {
                basketMapper.addProductToBasket_Product(client, currentProduct);
            } else {
                currentProduct.setCount(resultProduct.getCount() + currentProduct.getCount());
                basketMapper.updateProductInBasket_Product(currentProduct);
            }

            return currentProduct;
        } catch (RuntimeException ex) {
            LOGGER.error("Can't add product to basket Product {} {}", currentProduct, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }


    @Override
    public Basket getAllProductsFromBasket(Client client) {
        LOGGER.debug("DAO get all products from basket Client {}", client);
        try {
            return basketMapper.getAllProductsFromBasket(client);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get all products from basket Client {} {}", client, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public void deleteProductFromBasket(int productId) {
        LOGGER.debug("DAO delete product from basket {}", productId);
        try {
            basketMapper.deleteProductFromBasket(productId);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't delete product from basket {} {}", productId, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public void updateProductInBasket(Product product) {
        LOGGER.debug("DAO update product in basket Product {}", product);
        try {
            basketMapper.updateProductInBasket_Product(product);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't update product in basket Product {} {}", product, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

}
