package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.UserTestBase;
import net.thumbtack.onlineshop.daoimpl.ClientDaoImpl;
import net.thumbtack.onlineshop.daoimpl.ProductDaoImpl;
import net.thumbtack.onlineshop.daoimpl.PurchaseDaoImpl;
import net.thumbtack.onlineshop.dto.response.ClientDtoResponse;
import net.thumbtack.onlineshop.dto.response.ProductDtoResponse;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.model.Client;
import net.thumbtack.onlineshop.model.Product;
import net.thumbtack.onlineshop.model.Purchase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Date;

import static net.thumbtack.onlineshop.exception.AppErrorCode.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class PurchaseDaoImplTest extends UserTestBase {

    @Autowired
    private PurchaseDaoImpl purchaseDao;
    @Autowired
    private ClientDaoImpl clientDao;
    @Autowired
    private ProductDaoImpl productDao;

    @Test
    public void testPurchaseProductWithInvalidVersion() throws IOException {
        try {
            ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
            String tokenAdmin = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

            ResponseEntity registerClient = createAndRegisterClient("Иван", "Иванов", "Иванович", "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient", "Password1!", SUCCESS);
            String tokenClient = registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
            ClientDtoResponse clientResponse = (ClientDtoResponse) registerClient.getBody();
            ClientDtoResponse updateDepositResponse = (ClientDtoResponse) putDeposit(tokenClient, 120000, SUCCESS).getBody();

            Client client = clientDao.getClientsWithPurchasesById(clientResponse.getId());

            ProductDtoResponse testingProduct = getTestingProduct(tokenAdmin);

            Product currentProduct = new Product(testingProduct.getId(), testingProduct.getName(), testingProduct.getPrice(), 4);
            Product onStorageProduct = productDao.getProductById(testingProduct.getId());

            //TODO try purchase product with invalid version
            onStorageProduct.setVersion(1234);

            Purchase purchase = new Purchase(new Date(), currentProduct.getPrice() * currentProduct.getCount());
            client.getDeposit().setAmount(client.getDeposit().getAmount() - purchase.getAmount());
            purchaseDao.purchaseProduct(client, purchase, currentProduct, onStorageProduct);

            ClientDtoResponse clientDtoResponse = (ClientDtoResponse) getInfoAboutCurUser(tokenClient, registerClient, SUCCESS).getBody();
            //TODO check purchase product or not
            assertEquals(updateDepositResponse.getDeposit(), clientDtoResponse.getDeposit());

        } catch (DaoException ex) {
            assertEquals(DATABASE_ERROR, ex.getErrorCode());
        }
    }

    @Test()
    public void testPurchaseProduct() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        String tokenAdmin = registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        ResponseEntity registerClient = createAndRegisterClient("Иван", "Иванов", "Иванович", "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient", "Password1!", SUCCESS);
        String tokenClient = registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        ClientDtoResponse clientResponse = (ClientDtoResponse) registerClient.getBody();
        ClientDtoResponse updateDepositResponse = (ClientDtoResponse) putDeposit(tokenClient, 120000, SUCCESS).getBody();

        Client client = clientDao.getClientsWithPurchasesById(clientResponse.getId());

        ProductDtoResponse testingProduct = getTestingProduct(tokenAdmin);

        Product currentProduct = new Product(testingProduct.getId(), testingProduct.getName(), testingProduct.getPrice(), 4);
        Product onStorageProduct = productDao.getProductById(testingProduct.getId());

        Purchase purchase = new Purchase(new Date(), currentProduct.getPrice() * currentProduct.getCount());
        client.getDeposit().setAmount(client.getDeposit().getAmount() - purchase.getAmount());
        purchaseDao.purchaseProduct(client, purchase, currentProduct, onStorageProduct);

        assertEquals(client.getDeposit().getAmount(), updateDepositResponse.getDeposit() - purchase.getAmount());
    }

}
