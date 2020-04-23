package net.thumbtack.onlineshop;


import net.thumbtack.onlineshop.dto.response.CategoryDtoResponse;
import net.thumbtack.onlineshop.dto.response.ProductDtoResponse;
import net.thumbtack.onlineshop.dto.response.ProductWithAboveCategoryNameDtoResponse;
import net.thumbtack.onlineshop.dto.response.ProductWithCategoryNameDtoResponse;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static net.thumbtack.onlineshop.exception.AppErrorCode.*;
import static org.junit.Assert.assertEquals;

public class ProductControllerTest extends UserTestBase {

    @Test
    public void testAddProduct() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        String token = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        ResponseEntity categoryOne = addCategory(token, "bmw", null, SUCCESS);
        CategoryDtoResponse categoryDtoResponseOne = (CategoryDtoResponse) categoryOne.getBody();

        ResponseEntity subOneResponse = addCategory(token, "bmw x5", categoryDtoResponseOne.getId(), SUCCESS);
        CategoryDtoResponse subOne = (CategoryDtoResponse) subOneResponse.getBody();

        ResponseEntity subTwoResponse = addCategory(token, "bmw x6", categoryDtoResponseOne.getId(), SUCCESS);
        CategoryDtoResponse subTwo = (CategoryDtoResponse) subTwoResponse.getBody();

        ResponseEntity productOne = addProduct(token, "Glass", 800, 200, Arrays.asList(subOne.getId(), subTwo.getId()), SUCCESS);
        ProductDtoResponse productDtoResponseOne = (ProductDtoResponse) productOne.getBody();

        ResponseEntity productTwo = addProduct(token, "Backpack", 800, 200, null, SUCCESS);
        ProductDtoResponse productDtoResponseTwo = (ProductDtoResponse) productTwo.getBody();
    }

    @Test
    public void testAddInvalidWithInvalidProductFields() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        String token = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        ProductDtoResponse testingProduct = getTestingProduct(token);

        ResponseEntity productFive = addProduct(token, " ", -800, -200, null, WRONG_NAME, WRONG_PRODUCT_PRICE, WRONG_PRODUCT_COUNT);
    }

    @Test
    public void testAddProductWithEmptyName() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        String token = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        ProductDtoResponse testingProduct = getTestingProduct(token);

        ResponseEntity productTwo = addProduct(token, "", 800, 200, null, WRONG_NAME);
    }

    @Test
    public void testAddProductWithInvalidPrice() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        String token = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        ProductDtoResponse testingProduct = getTestingProduct(token);

        ResponseEntity productThree = addProduct(token, "Backpack", 0, 200, null, WRONG_PRODUCT_PRICE);
    }

    @Test
    public void testAddProductWithInvalidCount() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        String token = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        ProductDtoResponse testingProduct = getTestingProduct(token);

        ResponseEntity productFour = addProduct(token, "Backpack", 800, -200, null, WRONG_PRODUCT_COUNT);
    }

    @Test
    public void testUpdateProductPriceAndCount() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        String token = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        ProductDtoResponse testingProduct = getTestingProduct(token);

        ProductDtoResponse updateProductBodyOne = (ProductDtoResponse) updateProduct(token, testingProduct.getId(), null, 801, 201, testingProduct.getCategories(), SUCCESS).getBody();

        checkProductFields(testingProduct.getName(), 801, 201, testingProduct.getCategories(), updateProductBodyOne);
    }

    @Test
    public void testUpdateProductWithNullNameAndPrice() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        String token = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        ProductDtoResponse testingProduct = getTestingProduct(token);

        ProductDtoResponse updateProductBodyTwo = (ProductDtoResponse) updateProduct(token, testingProduct.getId(), null, null, 201, testingProduct.getCategories(), SUCCESS).getBody();
        checkProductFields(testingProduct.getName(), testingProduct.getPrice(), 201, testingProduct.getCategories(), updateProductBodyTwo);
    }

    @Test
    public void testUpdateProductWithNullFields() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        String token = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        ProductDtoResponse testingProduct = getTestingProduct(token);
        ProductDtoResponse updateProductBodyThree = (ProductDtoResponse) updateProduct(token, testingProduct.getId(), null, null, null, testingProduct.getCategories(), SUCCESS).getBody();

        checkProductFields(testingProduct.getName(), testingProduct.getPrice(), testingProduct.getCount(), testingProduct.getCategories(), updateProductBodyThree);

    }

    @Test
    public void testUpdateProductCategories() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        String token = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        ProductDtoResponse testingProduct = getTestingProduct(token);

        ResponseEntity categoryOne = addCategory(token, "ford", null, SUCCESS);
        CategoryDtoResponse categoryDtoResponseOne = (CategoryDtoResponse) categoryOne.getBody();

        ResponseEntity subOneResponse = addCategory(token, "ford focus", categoryDtoResponseOne.getId(), SUCCESS);
        CategoryDtoResponse subOne = (CategoryDtoResponse) subOneResponse.getBody();

        ResponseEntity updateProductThree = updateProduct(token, testingProduct.getId(), null, 30, 0, Arrays.asList(subOne.getId(), categoryDtoResponseOne.getId()), SUCCESS);

        ProductDtoResponse updateProductBodyThree = (ProductDtoResponse) updateProductThree.getBody();

        if (updateProductBodyThree.getCategories() != null) {
            assertEquals(testingProduct.getCategories().size(), updateProductBodyThree.getCategories().size());
        }

    }

    @Test
    public void testDeleteProduct() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        String token = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        ProductDtoResponse testingProduct = getTestingProduct(token);
        deleteProduct(token, testingProduct.getId(), SUCCESS);
    }

    @Test
    public void testGetInfoAboutProduct() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        String token = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        ProductDtoResponse testingProduct = getTestingProduct(token);
        ResponseEntity result = getInfoAboutProduct(token, testingProduct.getId(), SUCCESS);

    }

    @Test
    public void testGetProductsByParamProduct() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login1", "Password1!", SUCCESS);
        String token = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        List<ProductDtoResponse> productList = preTestingProcess();

        List<ProductWithCategoryNameDtoResponse> result = getProductsByParamProduct(token, "product", SUCCESS).getResultsByOrderProductOrCategoryList();

        assertEquals(productList.size(), result.size());
    }

    @Test
    public void testGetProductsByParamCategory() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login1", "Password1!", SUCCESS);
        String token = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        List<ProductDtoResponse> productList = preTestingProcess();

        Set<ProductWithAboveCategoryNameDtoResponse> result = getProductsByParamCategory(token, "category", SUCCESS).getResultsByOrderCategory();

        assertEquals(productList.get(0).getCategories().size(), result.size());
    }

    @Test
    public void testGetProductsByParamCategoryList() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login1", "Password1!", SUCCESS);
        String token = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        List<ProductDtoResponse> productList = preTestingProcess();

        List<ProductWithCategoryNameDtoResponse> result = getProductsByParamCategoryList(token, productList.get(0).getCategories(), SUCCESS).getResultsByOrderProductOrCategoryList();

        assertEquals(productList.size(), result.size());

    }

}

