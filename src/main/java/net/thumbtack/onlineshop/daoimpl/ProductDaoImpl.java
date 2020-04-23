package net.thumbtack.onlineshop.daoimpl;

import net.thumbtack.onlineshop.dao.ProductDao;
import net.thumbtack.onlineshop.exception.AppErrorCode;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.model.Product;
import net.thumbtack.onlineshop.mybatis.mappers.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static net.thumbtack.onlineshop.exception.AppErrorCode.WRONG_VERSION_FOR_UPDATE;

@Component
public class ProductDaoImpl implements ProductDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDaoImpl.class);
    private ProductMapper productMapper;

    public ProductDaoImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public List<Integer> updateProductCategories(int productId, List<Integer> categories) {
        return null;
    }

    @Transactional
    @Override
    public Product addProduct(Product product) {
        LOGGER.debug("DAO add product Product {}", product);
        try {
            Product productOnStorage = productMapper.getProductByName(product.getName());
            if (productOnStorage != null) {
                productOnStorage.setCount(productOnStorage.getCount() + product.getCount());

                checkSuccessUpdateProductCount(productMapper.updateProductCount(productOnStorage));
                return productOnStorage;
            } else {
                productMapper.addProduct(product);
                if (product.getCategories() != null) {
                    if (product.getCategories().size() != 0) {
                        productMapper.setCategories(product.getId(), product.getCategories());
                    }
                }
                return product;
            }
        } catch (RuntimeException ex) {
            LOGGER.error("Can't add product Product{} {}", product, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    public void checkSuccessUpdateProductCount(int resultUpdate) {
        if (resultUpdate != 1) {
            throw new DaoException(WRONG_VERSION_FOR_UPDATE);
        }
    }

    @Transactional
    @Override
    public Product updateProduct(Product product) {
        LOGGER.debug("DAO update product Product {}", product);
        try {
            productMapper.updateProduct(product);
            productMapper.deleteCurrentProductCategories(product);
            productMapper.setCategories(product.getId(), product.getCategories());
            return product;
        } catch (RuntimeException ex) {
            LOGGER.error("Can't update product Product {} {}", product, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public Product getProductById(int productId) {
        LOGGER.debug("DAO get product by Id {}", productId);
        try {
            return productMapper.getProductById(productId);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get product by Id{} {}", productId, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public void deleteProduct(int productId) {
        LOGGER.debug("DAO delete product by Id {}", productId);
        try {
            productMapper.deleteProductById(productId);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't delete product by Id{} {}", productId, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public List<Product> getAllProductsByParams(List<Integer> category, String order) {
        LOGGER.debug("DAO get all products by param {}, {}", category, order);
        try {
            return productMapper.getAllProductsByParams(category, order);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get all products by param {},{} {}", category, order, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public List<Product> getProductsByListId(List<Integer> product) {
        LOGGER.debug("DAO get all products by param {}, {}", product);
        try {
            return productMapper.getProductsByListId(product);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get all products by param {}, {}", product, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }
}