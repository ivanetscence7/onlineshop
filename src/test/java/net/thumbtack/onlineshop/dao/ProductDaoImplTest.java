package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.UserTestBase;
import net.thumbtack.onlineshop.daoimpl.DepositDaoImpl;
import net.thumbtack.onlineshop.daoimpl.ProductDaoImpl;
import net.thumbtack.onlineshop.dto.response.ProductDtoResponse;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.model.Product;
import net.thumbtack.onlineshop.mybatis.mappers.ProductMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static net.thumbtack.onlineshop.exception.AppErrorCode.SUCCESS;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductDaoImplTest extends UserTestBase {

    @Autowired
    private ProductDaoImpl productDao;

    @Autowired
    private ProductMapper productMapper;

    @Test(expected = DaoException.class)
    public void testUpdateProductWithInvalidVersion() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        String token = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        ProductDtoResponse testingProduct = getTestingProduct(token);

        Product product = productDao.getProductById(testingProduct.getId());
        product.setCount(350);
        product.setVersion(1234);
        //TODO try update product count without version
        Product updateProduct = productDao.updateProduct(product);
        assertEquals(product, updateProduct);
    }

    @Test
    public void testUpdateProductSuccess() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        String token = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        ProductDtoResponse testingProduct = getTestingProduct(token);

        Product product = productDao.getProductById(testingProduct.getId());
        product.setCount(350);
        productDao.checkSuccessUpdateProductCount(productMapper.updateProductCount(product));
    }


}
