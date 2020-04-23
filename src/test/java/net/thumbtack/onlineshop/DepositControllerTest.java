package net.thumbtack.onlineshop;

import net.thumbtack.onlineshop.dto.response.ClientDtoResponse;
import net.thumbtack.onlineshop.model.Deposit;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static net.thumbtack.onlineshop.exception.AppErrorCode.INVALID_DEPOSIT;
import static net.thumbtack.onlineshop.exception.AppErrorCode.SUCCESS;

public class DepositControllerTest extends UserTestBase {
    @Test
    public void testPutDeposit() throws IOException {
        ResponseEntity responseEntity = createAndRegisterClient("Иван", "Иванов", "Иванович", "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient", "Password1!", SUCCESS);
        String token = responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        putDeposit(token, 120000, SUCCESS);
    }

    @Test
    public void testGetClientDeposit() throws IOException {
        ResponseEntity registerClient = createAndRegisterClient("Иван", "Иванов", "Иванович", "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient", "Password1!", SUCCESS);
        ResponseEntity putDepositClient = putDeposit(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), 120000, SUCCESS);

        ClientDtoResponse clientResponse = (ClientDtoResponse) putDepositClient.getBody();
        getClientDeposit(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), clientResponse.getDeposit(), SUCCESS);
    }

    @Test
    public void testPutNegativeDeposit() throws IOException {
        ResponseEntity responseEntity = createAndRegisterClient("Иван", "Иванов", "Иванович", "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient", "Password1!", SUCCESS);
        putDeposit(responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE), -120000, INVALID_DEPOSIT);
    }

}