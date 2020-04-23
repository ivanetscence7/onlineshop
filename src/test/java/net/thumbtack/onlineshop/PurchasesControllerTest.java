package net.thumbtack.onlineshop;


import net.thumbtack.onlineshop.dto.request.ProductForPurchaseDtoRequest;
import net.thumbtack.onlineshop.dto.response.*;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static net.thumbtack.onlineshop.exception.AppErrorCode.SUCCESS;
import static org.junit.Assert.assertEquals;

public class PurchasesControllerTest extends UserTestBase {

    @Test
    public void testPurchaseProduct() throws IOException {

        List<ProductDtoResponse> productListForTest = preTestingProcess();

        ResponseEntity registerClient = createAndRegisterClient("Иван", "Иванов", "Иванович",
                "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient", "Password1!", SUCCESS);
        String token = registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        ResponseEntity putDepositClient = putDeposit(token, 120000, SUCCESS);
        ClientDtoResponse putDepositClientBody = (ClientDtoResponse) putDepositClient.getBody();

        productListForTest.get(0).setCount(5);
        ResponseEntity responseEntity = purchaseProduct(token, productListForTest.get(0), SUCCESS);
        ProductDtoResponse productDtoResponse = (ProductDtoResponse) responseEntity.getBody();

        ClientDtoResponse infoAboutCurUserClient = (ClientDtoResponse) getInfoAboutCurUser(token, registerClient, SUCCESS).getBody();

        Integer depositBeforePurchase = putDepositClientBody.getDeposit();
        Integer costPurchase = productDtoResponse.getCount() * productDtoResponse.getPrice();
        Integer resultDeposit = depositBeforePurchase - costPurchase;

        assertEquals(infoAboutCurUserClient.getDeposit(), resultDeposit);
    }

    @Test

    public void testBuyProductsFromBasket() throws IOException {
        List<ProductDtoResponse> productListForTest = preTestingProcess();

        ResponseEntity registerClient = createAndRegisterClient("Иван", "Иванов", "Иванович",
                "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient", "Password1!", SUCCESS);
        String token = registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        ResponseEntity putDepositClient = putDeposit(token, 120000, SUCCESS);
        ClientDtoResponse putDepositClientBody = (ClientDtoResponse) putDepositClient.getBody();

        ResponseEntity productsInBasketOne = addProductToBasket(token, productListForTest.get(0), SUCCESS);
        ProductsInBasketDtoResponse productsInBasketOneBody = (ProductsInBasketDtoResponse) productsInBasketOne.getBody();
        assertEquals(1, productsInBasketOneBody.getProducts().size());

        ResponseEntity productsInBasketTwo = addProductToBasket(token, productListForTest.get(1), SUCCESS);
        ProductsInBasketDtoResponse productsInBasketTwoBody = (ProductsInBasketDtoResponse) productsInBasketTwo.getBody();
        assertEquals(2, productsInBasketTwoBody.getProducts().size());

        deleteProductFromBasket(token, productListForTest.get(0).getId(), SUCCESS);

        ResponseEntity basketContent = getBasketContent(token, SUCCESS);

        ProductsInBasketDtoResponse productsInBasket = (ProductsInBasketDtoResponse) basketContent.getBody();

        List<ProductForPurchaseDtoRequest> purchaseDtoRequests = new ArrayList<>();
        productsInBasketTwoBody.getProducts().forEach(product ->
                purchaseDtoRequests.add(new ProductForPurchaseDtoRequest(product.getId(), product.getName(), product.getPrice(), product.getCount())));

        purchaseDtoRequests.add(new ProductForPurchaseDtoRequest(500, "tire", 1234, 999));

        PurchasesFromBasketDtoResponse purchases = (PurchasesFromBasketDtoResponse) buyProductsFromBasket(token, purchaseDtoRequests, SUCCESS).getBody();

        assertEquals(2, purchases.getBought().size());
        assertEquals(1, purchases.getRemaining().size());
    }

    @Test
    public void testPurchaseStatement() throws IOException {
        List<ProductDtoResponse> productListForTest = preTestingProcess();

        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login1", "Password1!", SUCCESS);
        String tokenAdmin = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        ResponseEntity registerClient = createAndRegisterClient("Иван", "Иванов", "Иванович",
                "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient", "Password1!", SUCCESS);
        String tokenClient = registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        ResponseEntity putDepositClient = putDeposit(tokenClient, 120000, SUCCESS);
        ClientDtoResponse putDepositClientBody = (ClientDtoResponse) putDepositClient.getBody();

        productListForTest.get(0).setCount(4);
        productListForTest.get(1).setCount(5);
        productListForTest.get(2).setCount(6);

        ProductDtoResponse purchaseProductOne = (ProductDtoResponse) purchaseProduct(tokenClient, productListForTest.get(0), SUCCESS).getBody();
        ProductDtoResponse purchaseProductTwo = (ProductDtoResponse) purchaseProduct(tokenClient, productListForTest.get(1), SUCCESS).getBody();
        ProductDtoResponse purchaseProductThree = (ProductDtoResponse) purchaseProduct(tokenClient, productListForTest.get(2), SUCCESS).getBody();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        ResponseEntity statementDtoResponseOne = getStatementByDateAndCriterion(tokenAdmin, "2019-1-1", dateFormat.format(date).toString(), "UP", 1, 0, SUCCESS);
        AllPurchasesDtoResponse purchasesByDate = (AllPurchasesDtoResponse) statementDtoResponseOne.getBody();
        //assertEquals(purchaseProductTwo.getPrice() * purchaseProductTwo.getCount(), purchasesByDate.getProfit());

        ResponseEntity statementDtoResponseTwo = getStatementByDateRange(tokenAdmin, "2019-1-1", dateFormat.format(date).toString(), "UP", SUCCESS);
        AllPurchasesDtoResponse purchasesByDateTwo = (AllPurchasesDtoResponse) statementDtoResponseTwo.getBody();

//        assertEquals(purchaseProductOne.getPrice() * purchaseProductOne.getCount() + purchaseProductTwo.getPrice() * purchaseProductTwo.getCount() + purchaseProductThree.getPrice() * purchaseProductThree.getCount(),
//                purchasesByDateTwo.getProfit());

    }
}

