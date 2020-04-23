package net.thumbtack.onlineshop;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static net.thumbtack.onlineshop.exception.AppErrorCode.*;

public class AdminControllerTest extends UserTestBase {

    @Test
    public void testRegistrationAdmin() throws IOException {
        createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login1", "Password1!", SUCCESS);
        createAndRegisterAdmin("Иван", "Иванов",  "АДМИН", "login2", "Password1!", SUCCESS);
    }

    @Test
    public void testRegistrationAdminWithExistsLogin() throws IOException {
        createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login1", "Password1!", SUCCESS);
        createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login1", "Password1!", LOGIN_ALREADY_EXISTS);
    }

    @Test
    public void testUpdateAdmin() throws IOException {
        ResponseEntity responseEntity = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        updateAdmin(responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE),
                "Петр", "Иванов", "Иванович", "АДМИН", "Password1!","Password2!", SUCCESS);
    }

    @Test
    public void testUpdateAdminWithInvalidOldPassword() throws IOException {
        ResponseEntity responseEntity = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login", "Password1!", SUCCESS);
        updateAdmin(responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE),
                "Петр", "Иванов", "Иванович", "АДМИН", "Password2!","Password2!", WRONG_PASSWORD);
    }
}

