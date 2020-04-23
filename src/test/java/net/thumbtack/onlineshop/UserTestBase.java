package net.thumbtack.onlineshop;


import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.onlineshop.config.AppProperties;
import net.thumbtack.onlineshop.dto.request.*;
import net.thumbtack.onlineshop.dto.response.*;
import net.thumbtack.onlineshop.exception.AppErrorCode;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static net.thumbtack.onlineshop.exception.AppErrorCode.SUCCESS;
import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserTestBase {

    private RestTemplate template = new RestTemplate();

    private static String urlPrefix = "http://localhost:8080/api";

    @Autowired
    protected ObjectMapper mapper;

    @BeforeClass
    public static void initSettings() {
        AppProperties.initSettings();
    }

    @Before
    public void before() {
        debugDelete("/debug/clear", EmptyResponse.class, null, null);
    }

    protected ResponseEntity createAndRegisterAdmin(String firstName, String lastName, String position, String login, String password, AppErrorCode... errorCodes) throws IOException {
        try {
            UserDtoRequest adminDtoRequest =
                    new AdminDtoRequest(firstName, lastName, login, password, position);
            ResponseEntity response = post("/admins", AdminDtoResponse.class, adminDtoRequest, null);
            checkAdminFields(firstName, lastName, position, (AdminDtoResponse) response.getBody());
            assertEquals(SUCCESS, errorCodes[0]);
            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity createAndRegisterAdmin(String firstName, String lastName, String patronymic,
                                                    String position, String login, String password, AppErrorCode... errorCodes) throws IOException {
        try {
            AdminDtoRequest adminDtoRequest =
                    new AdminDtoRequest(firstName, lastName, patronymic, login, password, position);
            ResponseEntity response = post("/admins", AdminDtoResponse.class, adminDtoRequest, null);
            checkAdminFields(firstName, lastName, patronymic, position, (AdminDtoResponse) response.getBody());
            assertEquals(SUCCESS, errorCodes[0]);
            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity createAndRegisterClient(String firstName, String lastName, String patronymic,
                                                     String email, String address, String phone, String login, String password, AppErrorCode... errorCodes) throws IOException {
        try {
            ClientDtoRequest clientDtoRequest =
                    new ClientDtoRequest(firstName, lastName, patronymic, login, password, email, address, phone);
            ResponseEntity response = post("/clients", ClientDtoResponse.class, clientDtoRequest, null);

            checkClientFields(firstName, lastName, patronymic, email, address, phone, (ClientDtoResponse) response.getBody());

            assertEquals(SUCCESS, errorCodes[0]);
            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity updateAdmin(String token, String firstName, String lastName, String patronymic,
                                         String position, String oldPassword, String newPassword, AppErrorCode... errorCodes) throws IOException {
        try {
            UpdateAdminDtoRequest updateAdminDtoRequest = new UpdateAdminDtoRequest(firstName, lastName, patronymic, oldPassword, newPassword, position);
            ResponseEntity responseEntity = put("/admins", AdminDtoResponse.class, updateAdminDtoRequest, token);
            checkAdminFields(firstName, lastName, patronymic, position, (AdminDtoResponse) responseEntity.getBody());
            return responseEntity;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity updateClient(String token, String firstName, String lastName, String patronymic,
                                          String email, String address, String phone, String oldPassword, String newPassword, AppErrorCode... errorCodes) throws IOException {
        try {
            UpdateClientDtoRequest updateClientDtoRequest = new UpdateClientDtoRequest(firstName, lastName, patronymic, oldPassword, newPassword, email, address, phone);

            ResponseEntity responseEntity = put("/clients", ClientDtoResponse.class, updateClientDtoRequest, token);
            assertEquals(SUCCESS, errorCodes[0]);
            checkClientFields(firstName, lastName, patronymic, email, address, phone, (ClientDtoResponse) responseEntity.getBody());
            return responseEntity;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity putDeposit(String token, Integer deposit, AppErrorCode... errorCodes) throws IOException {
        try {
            DepositDtoRequest depositDtoRequest = new DepositDtoRequest(deposit);
            ResponseEntity responseEntity = put("/deposits", ClientDtoResponse.class, depositDtoRequest, token);
            checkDepositField(deposit, ((ClientDtoResponse) responseEntity.getBody()).getDeposit());
            return responseEntity;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity getClientDeposit(String token, Integer deposit, AppErrorCode... errorCodes) throws IOException {
        try {
            ResponseEntity response = get("/deposits", ClientDtoResponse.class, null, token);
            ClientDtoResponse clientResponse = (ClientDtoResponse) response.getBody();

            checkDepositField(deposit, clientResponse.getDeposit());
            assertEquals(SUCCESS, errorCodes[0]);
            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity getInfoAboutClients(String token, AppErrorCode... errorCodes) throws IOException {
        try {
            ResponseEntity responseEntity = get("/clients", InfoAboutAllClientDtoResponse.class, null, token);
            InfoAboutAllClientDtoResponse response = (InfoAboutAllClientDtoResponse) responseEntity.getBody();

            assertEquals(SUCCESS, errorCodes[0]);
            return responseEntity;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity getInfoAboutCurUser(String token, ResponseEntity registerUser, AppErrorCode... errorCodes) throws IOException {
        try {
            ResponseEntity responseEntity = get("/accounts", UserDtoResponse.class, null, token);
            UserDtoResponse inputUserResponse = (UserDtoResponse) responseEntity.getBody();
            assertNotNull(inputUserResponse);
            assertEquals(SUCCESS, errorCodes[0]);
            return responseEntity;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }


    protected ResponseEntity logout(String token, AppErrorCode... errorCodes) throws IOException {
        try {
            ResponseEntity responseEntity = delete("/sessions", EmptyResponse.class, null, token);
            assertEquals(SUCCESS, errorCodes[0]);
            return responseEntity;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity login(String login, String password, AppErrorCode... errorCodes) throws IOException {
        try {
            LoginDtoRequest loginDtoRequest = new LoginDtoRequest(login, password);
            ResponseEntity responseEntity = post("/sessions", UserDtoResponse.class, loginDtoRequest, null);
            assertEquals(SUCCESS, errorCodes[0]);
            return responseEntity;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity addCategory(String token, String name, Integer parentId, AppErrorCode... errorCodes) throws IOException {
        try {
            CategoryDtoRequest categoryDtoRequest = new CategoryDtoRequest(name, parentId);
            ResponseEntity response = post("/categories", CategoryDtoResponse.class, categoryDtoRequest, token);
            CategoryDtoResponse categoryResponse = (CategoryDtoResponse) response.getBody();
            checkCategoryFields(name, parentId, categoryResponse);
            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }

    }

    protected ResponseEntity getCategoryById(String token, Integer id, AppErrorCode... errorCodes) throws IOException {
        try {
            ResponseEntity response = get("/categories/" + id, CategoryDtoResponse.class, null, token);
            CategoryDtoResponse categoryDtoResponse = (CategoryDtoResponse) response.getBody();
            assertNotNull(categoryDtoResponse);
            assertEquals(SUCCESS, errorCodes[0]);
            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity updateCategory(String token, Integer id, String newName, Integer newParentId, AppErrorCode... errorCodes) throws IOException {
        try {
            CategoryDtoRequest categoryDtoRequest = new CategoryDtoRequest(newName, newParentId);
            ResponseEntity response = put("/categories/" + id, CategoryDtoResponse.class, categoryDtoRequest, token);
            CategoryDtoResponse categoryDtoResponse = (CategoryDtoResponse) response.getBody();
            assertNotNull(categoryDtoResponse);
            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity deleteCategory(String token, Integer id, AppErrorCode... errorCodes) throws IOException {
        try {
            ResponseEntity response = delete("/categories/" + id, EmptyResponse.class, null, token);
            assertTrue(EmptyResponse.class.isAssignableFrom(response.getBody().getClass()));
            assertTrue(response.getBody() instanceof EmptyResponse);
            assertNotNull(response);

            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity getCategories(String token, AppErrorCode... errorCodes) throws IOException {
        try {
            ResponseEntity response = get("/categories", AllCategoryDtoResponse.class, null, token);
            AllCategoryDtoResponse categories = (AllCategoryDtoResponse) response.getBody();
            assertNotNull(categories);

            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity addProduct(String token, String name, int price, int count, List<Integer> categories, AppErrorCode... errorCodes) throws IOException {
        try {
            ProductDtoRequest productDtoRequest = new ProductDtoRequest(name, price, count, categories);
            ResponseEntity response = post("/products", ProductDtoResponse.class, productDtoRequest, token);
            checkProductFields(name, price, count, categories, (ProductDtoResponse) response.getBody());
            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity updateProduct(String token, Integer id, String name, Integer price, Integer count, List<Integer> categories, AppErrorCode... errorCodes) throws IOException {
        try {
            UpdateProductDtoRequest updateProductDtoRequest = new UpdateProductDtoRequest(name, price, count, categories);
            ResponseEntity response = put("/products/" + id, ProductDtoResponse.class, updateProductDtoRequest, token);
            ProductDtoResponse product = (ProductDtoResponse) response.getBody();
            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity deleteProduct(String token, Integer id, AppErrorCode... errorCodes) throws IOException {
        try {
            ResponseEntity response = delete("/products/" + id, EmptyResponse.class, null, token);
            assertTrue(EmptyResponse.class.isAssignableFrom(response.getBody().getClass()));
            assertTrue(response.getBody() instanceof EmptyResponse);
            assertNotNull(response);
            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity getInfoAboutProduct(String token, Integer id, AppErrorCode... errorCodes) throws IOException {
        try {
            ResponseEntity response = get("/products/" + id, ProductWithCategoryNameDtoResponse.class, null, token);
            ProductWithCategoryNameDtoResponse productDtoResponse = (ProductWithCategoryNameDtoResponse) response.getBody();
            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity purchaseProduct(String token, ProductDtoResponse productDtoResponse, AppErrorCode... errorCodes) throws IOException {
        try {
            ProductForPurchaseDtoRequest productForPurchaseDtoRequest =
                    new ProductForPurchaseDtoRequest(productDtoResponse.getId(),
                            productDtoResponse.getName(),
                            productDtoResponse.getPrice(),
                            productDtoResponse.getCount());

            ResponseEntity response = post("/purchases", ProductDtoResponse.class, productForPurchaseDtoRequest, token);
            ProductDtoResponse purchaseProductDtoResponse = (ProductDtoResponse) response.getBody();

            checkPurchaseProductFields(productDtoResponse.getId(), productDtoResponse.getName(), productDtoResponse.getPrice(), productDtoResponse.getCount(), purchaseProductDtoResponse);

            assertEquals(SUCCESS, errorCodes[0]);
            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity getStatementByDateAndCriterion(String token, String startDate, String endDate, String criterion, Integer limit, Integer offset, AppErrorCode... errorCodes) throws IOException {
        try {
            ResponseEntity response = get("/purchases?startDate=" + startDate + "&endDate=" + endDate + "&criterion=" + criterion + "&limit=" + limit + "&offset=" + offset,
                            AllPurchasesDtoResponse.class, null, token);
            assertEquals(SUCCESS, errorCodes[0]);
            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity getStatementByDateRange(String token, String startDate, String endDate, String criterion, AppErrorCode... errorCodes) throws IOException {
        try {
            ResponseEntity response = get("/purchases?startDate=" + startDate + "&endDate=" + endDate + "&criterion=" + criterion,
                    AllPurchasesDtoResponse.class, null, token);
            assertEquals(SUCCESS, errorCodes[0]);
            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }


    protected ResponseEntity addProductToBasket(String token, ProductDtoResponse productDtoResponse, AppErrorCode... errorCodes) throws IOException {
        try {
            ProductForPurchaseDtoRequest productForPurchaseDtoRequest =
                    new ProductForPurchaseDtoRequest(productDtoResponse.getId(), productDtoResponse.getName(), productDtoResponse.getPrice(), productDtoResponse.getCount());

            ResponseEntity response = post("/baskets", ProductsInBasketDtoResponse.class, productForPurchaseDtoRequest, token);
            ProductsInBasketDtoResponse productsInBasket = (ProductsInBasketDtoResponse) response.getBody();
            assertNotNull(productsInBasket.getProducts().size());
            assertTrue(productsInBasket.getProducts().size() > 0);

            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity deleteProductFromBasket(String token, Integer id, AppErrorCode... errorCodes) throws IOException {
        try {
            ResponseEntity response = delete("/baskets/" + id, EmptyResponse.class, null, token);
            assertTrue(EmptyResponse.class.isAssignableFrom(response.getBody().getClass()));
            assertTrue(response.getBody() instanceof EmptyResponse);
            assertNotNull(response);
            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity getBasketContent(String token, AppErrorCode... errorCodes) throws IOException {
        try {
            ResponseEntity response = get("/baskets", ProductsInBasketDtoResponse.class, null, token);
            ProductsInBasketDtoResponse productsInBasket = (ProductsInBasketDtoResponse) response.getBody();
            assertNotNull(productsInBasket.getProducts().size());
            assertTrue(productsInBasket.getProducts().size() > 0);
            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity buyProductsFromBasket(String token, List<ProductForPurchaseDtoRequest> products, AppErrorCode... errorCodes) throws IOException {
        try {
            ResponseEntity response = post("/purchases/baskets", PurchasesFromBasketDtoResponse.class, products, token);
            PurchasesFromBasketDtoResponse boughtAndRemainingProducts = (PurchasesFromBasketDtoResponse) response.getBody();

            assertNotNull(boughtAndRemainingProducts.getBought().size());
            assertNotNull(boughtAndRemainingProducts.getRemaining().size());

            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }


    protected AllProductsDtoResponse getProductsByParamProduct(String token, String requestParam, AppErrorCode... errorCodes) throws IOException {
        try {
            ResponseEntity response = get("/products?order=" + requestParam, AllProductsDtoResponse.class, null, token);
            return (AllProductsDtoResponse) response.getBody();
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected AllProductsDtoResponse getProductsByParamCategory(String token, String requestParam, AppErrorCode... errorCodes) throws IOException {
        try {
            ResponseEntity response = get("/products?order=" + requestParam, AllProductsDtoResponse.class, null, token);
            return (AllProductsDtoResponse) response.getBody();
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected AllProductsDtoResponse getProductsByParamCategoryList(String token, List<Integer> category, AppErrorCode... errorCodes) throws IOException {
        try {
            ResponseEntity response = get("/products?category=" + category.toString().substring(1, category.toString().length() - 1), AllProductsDtoResponse.class, null, token);
            return (AllProductsDtoResponse) response.getBody();
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    protected ResponseEntity updateProductCountInBasket(String token, ProductDtoResponse productDtoResponse, AppErrorCode... errorCodes) throws IOException {
        try {
            ProductForPurchaseDtoRequest productForPurchaseDtoRequest =
                    new ProductForPurchaseDtoRequest(productDtoResponse.getId(), productDtoResponse.getName(), productDtoResponse.getPrice(), productDtoResponse.getCount());

            ResponseEntity response = put("/baskets", ProductsInBasketDtoResponse.class, productForPurchaseDtoRequest, token);
            ProductsInBasketDtoResponse productsInBasket = (ProductsInBasketDtoResponse) response.getBody();

            assertNotNull(productsInBasket.getProducts().size());
            assertTrue(productsInBasket.getProducts().size() > 0);
            productsInBasket.getProducts().stream().filter(c -> productForPurchaseDtoRequest.getId() == (c.getId()))
                    .forEach(r -> assertEquals(r.getCount(), productForPurchaseDtoRequest.getCount()));
            return response;
        } catch (HttpClientErrorException ex) {
            FailedDtoResponse failedDtoResponse = mapper.readValue(ex.getResponseBodyAsString(), FailedDtoResponse.class);
            checkFailedDtoResponses(failedDtoResponse, errorCodes);
            return null;
        }
    }

    private void checkPurchaseProductFields(Integer id, String name, int price, int count, ProductDtoResponse purchaseProductDtoResponse) {
        assertEquals(id, purchaseProductDtoResponse.getId());
        assertEquals(name, purchaseProductDtoResponse.getName());
        assertEquals(price, purchaseProductDtoResponse.getPrice());
        assertEquals(count, purchaseProductDtoResponse.getCount());
    }

    private void checkDepositField(Integer deposit, Integer depositResponse) {
        assertEquals(deposit, depositResponse);
    }

    protected void checkProductFields(String name, int price, int count, List<Integer> categories, ProductDtoResponse product) {
        assertEquals(name, product.getName());
        assertEquals(price, product.getPrice());
        assertEquals(count, product.getCount());
        if (product.getCategories() != null) {
            assertEquals(categories.size(), product.getCategories().size());
        }

    }

    protected void checkCategoryFields(String name, Integer parentId, CategoryDtoResponse categoryResponse) {
        assertEquals(name, categoryResponse.getName());
    }

    protected void checkClientFields(String firstName, String lastName, String patronymic, String email, String address, String phone, ClientDtoResponse clientDtoResponse) {
        assertEquals(firstName, clientDtoResponse.getFirstName());
        assertEquals(lastName, clientDtoResponse.getLastName());
        assertEquals(patronymic, clientDtoResponse.getPatronymic());
        assertEquals(email, clientDtoResponse.getEmail());
        assertEquals(address, clientDtoResponse.getAddress());
        StringBuilder sb = new StringBuilder();
        Arrays.stream(phone.split("-")).forEach(sb::append);
        assertEquals(sb.toString(), clientDtoResponse.getPhone());
    }

    protected void checkAdminFields(String firstName, String lastName, String patronymic, String position, AdminDtoResponse adminDtoResponse) {
        checkAdminFields(firstName, lastName, position, adminDtoResponse);
        assertEquals(patronymic, adminDtoResponse.getPatronymic());
    }

    protected void checkAdminFields(String firstName, String lastName, String position, AdminDtoResponse adminDtoResponse) {
        assertEquals(firstName, adminDtoResponse.getFirstName());
        assertEquals(lastName, adminDtoResponse.getLastName());
        assertEquals(position, adminDtoResponse.getPosition());
    }

    protected void checkFailedDtoResponses(FailedDtoResponse failedDtoResponse, AppErrorCode... errorCodes) {
        List<AppErrorCode> errorCodeList = new ArrayList<>();
        assertEquals(errorCodes.length, failedDtoResponse.getErrors().size());
        for (FailedItemDtoResponse failedItem : failedDtoResponse.getErrors()) {
            errorCodeList.add(failedItem.getErrorCode());
        }
        for (AppErrorCode errorCode : errorCodes) {
            assertTrue(errorCodeList.contains(errorCode));
        }
    }


    protected ResponseEntity debugDelete(String url, Class<?> classResponse, Object requestBody, String cookie) throws HttpClientErrorException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        if (cookie != null) {
            headers.add(HttpHeaders.COOKIE, cookie);
        }
        HttpEntity<Object> requestEntity;
        if (requestBody != null) {
            requestEntity = new HttpEntity<>(requestBody, headers);
        } else {
            requestEntity = new HttpEntity<>(headers);
        }
        return template.exchange(urlPrefix + url, POST, requestEntity, classResponse);
    }

    protected ResponseEntity delete(String url, Class<?> classResponse, Object requestBody, String cookie) throws HttpClientErrorException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        if (cookie != null) {
            headers.add(HttpHeaders.COOKIE, cookie);
        }
        HttpEntity<Object> requestEntity;
        if (requestBody != null) {
            requestEntity = new HttpEntity<>(requestBody, headers);
        } else {
            requestEntity = new HttpEntity<>(headers);
        }
        return template.exchange(urlPrefix + url, DELETE, requestEntity, classResponse);
    }

    protected ResponseEntity post(String url, Class<?> classResponse, Object requestBody, String cookie) throws HttpClientErrorException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        if (cookie != null) {
            headers.add(HttpHeaders.COOKIE, cookie);
        }
        HttpEntity<Object> requestEntity;
        if (requestBody != null) {
            requestEntity = new HttpEntity<>(requestBody, headers);
        } else {
            requestEntity = new HttpEntity<>(headers);
        }
        return template.exchange(urlPrefix + url, POST, requestEntity, classResponse);
    }

    protected ResponseEntity put(String url, Class<?> classResponse, Object requestBody, String cookie) throws HttpClientErrorException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        if (cookie != null) {
            headers.add(HttpHeaders.COOKIE, cookie);
        }
        HttpEntity<Object> requestEntity;
        if (requestBody != null) {
            requestEntity = new HttpEntity<>(requestBody, headers);
        } else {
            requestEntity = new HttpEntity<>(headers);
        }
        return template.exchange(urlPrefix + url, PUT, requestEntity, classResponse);
    }

    protected ResponseEntity get(String url, Class<?> classResponse, Object requestBody, String cookie) throws HttpClientErrorException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        if (cookie != null) {
            headers.add(HttpHeaders.COOKIE, cookie);
        }
        HttpEntity<Object> requestEntity;
        if (requestBody != null) {
            requestEntity = new HttpEntity<>(requestBody, headers);
        } else {
            requestEntity = new HttpEntity<>(headers);
        }
        return template.exchange(urlPrefix + url, GET, requestEntity, classResponse);
    }

    protected ProductDtoResponse getTestingProduct(String token) throws IOException {
        ResponseEntity categoryOne = addCategory(token, "bmw", null, SUCCESS);
        CategoryDtoResponse categoryDtoResponseOne = (CategoryDtoResponse) categoryOne.getBody();

        ResponseEntity subOneResponse = addCategory(token, "bmw x5", categoryDtoResponseOne.getId(), SUCCESS);
        CategoryDtoResponse subOne = (CategoryDtoResponse) subOneResponse.getBody();

        ResponseEntity subTwoResponse = addCategory(token, "bmw x6", categoryDtoResponseOne.getId(), SUCCESS);
        CategoryDtoResponse subTwo = (CategoryDtoResponse) subTwoResponse.getBody();

        ResponseEntity productOne = addProduct(token, "Glass", 800, 200, Arrays.asList(subOne.getId(), subTwo.getId()), SUCCESS);
        ProductDtoResponse productDtoResponseOne = (ProductDtoResponse) productOne.getBody();

        return productDtoResponseOne;
    }

    public List<ProductDtoResponse> preTestingProcess() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        ///
        ResponseEntity categoryOne = addCategory(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE),
                "bmw", null, SUCCESS);
        CategoryDtoResponse categoryDtoResponseOne = (CategoryDtoResponse) categoryOne.getBody();

        ResponseEntity subOneResponse = addCategory(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE),
                "bmw x5", categoryDtoResponseOne.getId(), SUCCESS);
        CategoryDtoResponse subOne = (CategoryDtoResponse) subOneResponse.getBody();

        ResponseEntity subTwoResponse = addCategory(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE),
                "bmw x6", categoryDtoResponseOne.getId(), SUCCESS);
        CategoryDtoResponse subTwo = (CategoryDtoResponse) subTwoResponse.getBody();
        ///
        ResponseEntity productOne = addProduct(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE),
                "Kreslo", 100, 40, Arrays.asList(subOne.getId(), subTwo.getId()), SUCCESS);
        ProductDtoResponse productOneDtoResponse = (ProductDtoResponse) productOne.getBody();

        ResponseEntity productTwo = addProduct(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE),
                "Glass", 90, 30, Arrays.asList(subOne.getId(), subTwo.getId()), SUCCESS);
        ProductDtoResponse productTwoDtoResponse = (ProductDtoResponse) productTwo.getBody();

        ResponseEntity productThree = addProduct(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE),
                "Turbo", 80, 20, Arrays.asList(subOne.getId(), subTwo.getId()), SUCCESS);
        ProductDtoResponse productThreeDtoResponse = (ProductDtoResponse) productThree.getBody();

        List<ProductDtoResponse> productListForTest = new ArrayList<>();
        productListForTest.add(productOneDtoResponse);
        productListForTest.add(productTwoDtoResponse);
        productListForTest.add(productThreeDtoResponse);
        return productListForTest;
    }
}
