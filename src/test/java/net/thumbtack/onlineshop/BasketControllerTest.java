package net.thumbtack.onlineshop;


import net.thumbtack.onlineshop.dto.response.ProductDtoResponse;
import net.thumbtack.onlineshop.dto.response.ProductsInBasketDtoResponse;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import static net.thumbtack.onlineshop.exception.AppErrorCode.*;
import static org.junit.Assert.assertEquals;

public class BasketControllerTest extends UserTestBase {

    @Test
    public void testAddProductToBasket() throws IOException {
        List<ProductDtoResponse> productListForTest = preTestingProcess();

        ResponseEntity registerClient = createAndRegisterClient("Иван", "Иванов", "Иванович",
                "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient", "Password1!", SUCCESS);

        ResponseEntity productsInBasketOne = addProductToBasket(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), productListForTest.get(0), SUCCESS);
        ProductsInBasketDtoResponse productsInBasketOneBody = (ProductsInBasketDtoResponse) productsInBasketOne.getBody();
        assertEquals(1, productsInBasketOneBody.getProducts().size());

        ResponseEntity productsInBasketTwo = addProductToBasket(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), productListForTest.get(1), SUCCESS);
        ProductsInBasketDtoResponse productsInBasketTwoBody = (ProductsInBasketDtoResponse) productsInBasketTwo.getBody();
        assertEquals(2, productsInBasketTwoBody.getProducts().size());

        ResponseEntity productsInBasketThree = addProductToBasket(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), productListForTest.get(2), SUCCESS);
        ProductsInBasketDtoResponse productsInBasketThreeBody = (ProductsInBasketDtoResponse) productsInBasketThree.getBody();
        assertEquals(3, productsInBasketThreeBody.getProducts().size());
    }

    @Test
    public void testDeleteAndGetAllProductFromBasket() throws IOException {
        List<ProductDtoResponse> productListForTest = preTestingProcess();

        ResponseEntity registerClient = createAndRegisterClient("Иван", "Иванов", "Иванович",
                "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient", "Password1!", SUCCESS);

        ResponseEntity productsInBasketOne = addProductToBasket(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), productListForTest.get(0), SUCCESS);
        ProductsInBasketDtoResponse productsInBasketOneBody = (ProductsInBasketDtoResponse) productsInBasketOne.getBody();
        assertEquals(1, productsInBasketOneBody.getProducts().size());

        ResponseEntity productsInBasketTwo = addProductToBasket(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), productListForTest.get(1), SUCCESS);
        ProductsInBasketDtoResponse productsInBasketTwoBody = (ProductsInBasketDtoResponse) productsInBasketTwo.getBody();
        assertEquals(2, productsInBasketTwoBody.getProducts().size());

        deleteProductFromBasket(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), productListForTest.get(0).getId(), SUCCESS);

        ResponseEntity basketContent = getBasketContent(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), SUCCESS);

        ProductsInBasketDtoResponse productsInBasket = (ProductsInBasketDtoResponse) basketContent.getBody();
        assertEquals(1, productsInBasket.getProducts().size());
    }

    @Test
    public void testAddProductToBasketWithInvalidName() throws IOException {
        ResponseEntity registerClient = createAndRegisterClient("Иван", "Иванов", "Иванович",
                "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient", "Password1!", SUCCESS);

        List<ProductDtoResponse> productListForTest = preTestingProcess();

        ProductDtoResponse productOne = new ProductDtoResponse(productListForTest.get(0).getId(), "anotherName",
                productListForTest.get(0).getPrice(), productListForTest.get(0).getCount());
        ResponseEntity productsInBasketOne = addProductToBasket(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), productOne, WRONG_NAME);
        assertEquals(null, productsInBasketOne);
    }

    @Test
    public void testAddProductToBasketWithInvalidPrice() throws IOException {
        ResponseEntity registerClient = createAndRegisterClient("Иван", "Иванов", "Иванович",
                "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient", "Password1!", SUCCESS);

        List<ProductDtoResponse> productListForTest = preTestingProcess();

        ProductDtoResponse productTwo = new ProductDtoResponse(productListForTest.get(0).getId(), productListForTest.get(0).getName(),
                1234, productListForTest.get(0).getCount());
        ResponseEntity productsInBasketTwo = addProductToBasket(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), productTwo, WRONG_PRODUCT_PRICE);
        assertEquals(null, productsInBasketTwo);
    }

    @Test
    public void testAddProductToBasketWithAnyCount() throws IOException {
        ResponseEntity registerClient = createAndRegisterClient("Иван", "Иванов", "Иванович",
                "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient", "Password1!", SUCCESS);

        List<ProductDtoResponse> productListForTest = preTestingProcess();

        ProductDtoResponse productThree = new ProductDtoResponse(productListForTest.get(0).getId(), productListForTest.get(0).getName(),
                productListForTest.get(0).getPrice(), 12345);
        ResponseEntity productsInBasketThree = addProductToBasket(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), productThree, SUCCESS);
        ProductsInBasketDtoResponse productsInBasketThreeBody = (ProductsInBasketDtoResponse) productsInBasketThree.getBody();
        assertEquals(1, productsInBasketThreeBody.getProducts().size());
    }

    @Test
    public void testAddProductToBasketWithInvalidNameAndPrice() throws IOException {
        ResponseEntity registerClient = createAndRegisterClient("Иван", "Иванов", "Иванович",
                "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient", "Password1!", SUCCESS);

        List<ProductDtoResponse> productListForTest = preTestingProcess();

        ProductDtoResponse productFour = new ProductDtoResponse(productListForTest.get(0).getId(), "invalidName",
                12345, productListForTest.get(0).getCount());
        ResponseEntity productsInBasketFour = addProductToBasket(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), productFour, WRONG_NAME, WRONG_PRODUCT_PRICE);
        assertEquals(null, productsInBasketFour);
    }

    @Test
    public void testAddProductToBasketWithInvalidId() throws IOException {
        ResponseEntity registerClient = createAndRegisterClient("Иван", "Иванов", "Иванович",
                "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient", "Password1!", SUCCESS);

        List<ProductDtoResponse> productListForTest = preTestingProcess();

        ProductDtoResponse productFive = new ProductDtoResponse(111111, productListForTest.get(0).getName(),productListForTest.get(0).getPrice(), productListForTest.get(0).getCount());
        addProductToBasket(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), productFive, PRODUCT_HAS_BEEN_DELETED);

    }

    @Test
    public void testUpdateProductCountInBasket() throws IOException {
        List<ProductDtoResponse> productListForTest = preTestingProcess();

        ResponseEntity registerClient = createAndRegisterClient("Иван", "Иванов", "Иванович",
                "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient", "Password1!", SUCCESS);

        ResponseEntity productsInBasketOne = addProductToBasket(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), productListForTest.get(0), SUCCESS);
        ProductsInBasketDtoResponse productsInBasketOneBody = (ProductsInBasketDtoResponse) productsInBasketOne.getBody();
        assertEquals(1, productsInBasketOneBody.getProducts().size());

        ResponseEntity productsInBasketTwo = addProductToBasket(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), productListForTest.get(1), SUCCESS);
        ProductsInBasketDtoResponse productsInBasketTwoBody = (ProductsInBasketDtoResponse) productsInBasketTwo.getBody();
        assertEquals(2, productsInBasketTwoBody.getProducts().size());

        productsInBasketTwoBody.getProducts().get(0).setCount(23);
        updateProductCountInBasket(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), productsInBasketTwoBody.getProducts().get(0), SUCCESS);

        //TODO test update product in basket which not exists product on storage
        productsInBasketTwoBody.getProducts().get(0).setId(12345);
        updateProductCountInBasket(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), productsInBasketTwoBody.getProducts().get(0), SUCCESS);

        //TODO test update product in basket with invalid name
        productsInBasketTwoBody.getProducts().get(0).setName("invalidName");
        updateProductCountInBasket(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), productsInBasketTwoBody.getProducts().get(0), WRONG_NAME);

        //TODO test update product in basket with invalid price
        productsInBasketTwoBody.getProducts().get(0).setPrice(12345);
        updateProductCountInBasket(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), productsInBasketTwoBody.getProducts().get(0), WRONG_PRODUCT_PRICE);

        //TODO test update product in basket with invalid price and name
        updateProductCountInBasket(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), productsInBasketTwoBody.getProducts().get(0), WRONG_NAME, WRONG_PRODUCT_PRICE);

    }

}
