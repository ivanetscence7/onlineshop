package net.thumbtack.onlineshop;


import net.thumbtack.onlineshop.dto.response.InfoAboutAllClientDtoResponse;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static net.thumbtack.onlineshop.exception.AppErrorCode.*;
import static org.junit.Assert.assertEquals;

public class ClientControllerTest extends UserTestBase {

    @Test
    public void testClientRegistration() throws IOException {
        createAndRegisterClient("Иван", "Иванов", "Иванович",
                "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient1", "Password1!", SUCCESS);
        createAndRegisterClient("Петр", "Иванов", "Иванович",
                "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient2", "Password1!", SUCCESS);

    }

    @Test
    public void testRegistrationWithExistsLogin() throws IOException {
        createAndRegisterClient("Иван", "Иванов", "Иванович",
                "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient1", "Password1!", SUCCESS);
        createAndRegisterClient("Петр", "Иванов", "Иванович",
                "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient1", "Password1!", LOGIN_ALREADY_EXISTS);
    }

    @Test
    public void testUpdateClient() throws IOException {
        ResponseEntity responseEntity = createAndRegisterClient("Иван", "Иванов", "Иванович", "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient", "Password1!", SUCCESS);
        updateClient(responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE),
                "Иван", "Иванов", "Иванович", "ivan@mail.ru", "Пушкина 39,", "+7-923-697-90-06", "Password1!", "Password2!", SUCCESS);
    }

    @Test
    public void testUpdateClientWithInvalidOldPassword() throws IOException {
        ResponseEntity responseEntity = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        updateAdmin(responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE),
                "Петр", "Иванов", "Иванович", "АДМИН", "Password1!", "Password2!", WRONG_PASSWORD);
    }

    @Test
    public void testGetInfoAboutClients() throws IOException {
        ResponseEntity client1 = createAndRegisterClient("Иван", "Иванов", "Иванович", "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient1", "Password1!", SUCCESS);
        ResponseEntity client2 = createAndRegisterClient("Саша", "Иванов", "Иванович", "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient2", "Password1!", SUCCESS);
        ResponseEntity clienе3 = createAndRegisterClient("Петя", "Иванов", "Иванович", "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginclient3", "Password1!", SUCCESS);

        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);

        InfoAboutAllClientDtoResponse listClient = (InfoAboutAllClientDtoResponse)
                getInfoAboutClients(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE), SUCCESS).getBody();
        assertEquals(3, listClient.getResult().size());
    }


}
