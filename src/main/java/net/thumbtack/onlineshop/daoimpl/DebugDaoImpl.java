package net.thumbtack.onlineshop.daoimpl;

import net.thumbtack.onlineshop.dao.DebugDao;
import net.thumbtack.onlineshop.exception.AppErrorCode;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.mybatis.mappers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DebugDaoImpl implements DebugDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(DebugDaoImpl.class);
    private SessionMapper sessionMapper;
    private CategoryMapper categoryMapper;
    private ProductMapper productMapper;
    private PurchaseMapper purchaseMapper;
    private BasketMapper basketMapper;

    @Autowired
    public DebugDaoImpl(SessionMapper sessionMapper, CategoryMapper categoryMapper, ProductMapper productMapper, PurchaseMapper purchaseMapper, BasketMapper basketMapper) {
        this.sessionMapper = sessionMapper;
        this.categoryMapper = categoryMapper;
        this.productMapper = productMapper;
        this.purchaseMapper = purchaseMapper;
        this.basketMapper = basketMapper;
    }

    @Override
    public void clearDataBase() {
        LOGGER.debug("DAO clear database");
        try {
            productMapper.deleteAllProducts();
            categoryMapper.deleteAllCategories();
            sessionMapper.deleteAllUsers();
            purchaseMapper.deleteAllPurchases();
            basketMapper.cleanBasket();
        } catch (RuntimeException ex) {
            LOGGER.error("DAO can't clear database {}", ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }

    }
}
