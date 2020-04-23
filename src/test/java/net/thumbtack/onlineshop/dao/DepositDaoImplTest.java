package net.thumbtack.onlineshop.dao;


import net.thumbtack.onlineshop.UserTestBase;
import net.thumbtack.onlineshop.daoimpl.DepositDaoImpl;
import net.thumbtack.onlineshop.dto.response.ClientDtoResponse;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.model.Deposit;
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
public class DepositDaoImplTest extends UserTestBase {

    @Autowired
    private DepositDaoImpl depositDao;

    @Test(expected = DaoException.class)
    public void testPutDepositWithInvalidVersion() throws IOException {
        ClientDtoResponse client = (ClientDtoResponse) createAndRegisterClient("Иван", "Иванов", "Иванович", "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient1", "Password1!", SUCCESS).getBody();

        //TODO try put deposit without version
        Deposit deposit = new Deposit(12000);
        Deposit updateDeposit = depositDao.putDeposit(client.getId(), deposit);
        assertEquals(deposit, updateDeposit);
    }

    @Test
    public void testPutDepositSuccess() throws IOException {
        ResponseEntity registerClient = createAndRegisterClient("Иван", "Иванов", "Иванович", "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient", "Password1!", SUCCESS);
        ResponseEntity putDepositClient = putDeposit(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), 120000, SUCCESS);

        ClientDtoResponse client = (ClientDtoResponse) registerClient.getBody();

        Deposit deposit = depositDao.getDepositById(client.getId());
        deposit.setAmount(7777);
        Deposit updateDeposit = depositDao.putDeposit(client.getId(), deposit);
        assertEquals(deposit, updateDeposit);

    }
}

