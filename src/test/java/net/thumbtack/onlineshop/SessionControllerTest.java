package net.thumbtack.onlineshop;

import net.thumbtack.onlineshop.dto.response.AdminDtoResponse;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static net.thumbtack.onlineshop.exception.AppErrorCode.SUCCESS;

public class SessionControllerTest extends UserTestBase {

    @Test
    public void testLoginAndLogoutAdmin() throws IOException {
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "logintestOne", "Password1!", SUCCESS);
        logout(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE), SUCCESS);

        ResponseEntity responseEntityLogin = login("logintestOne", "Password1!", SUCCESS);
        if (responseEntityLogin != null) {
            AdminDtoResponse adminDtoResponse = (AdminDtoResponse) responseEntityLogin.getBody();
            checkAdminFields("Иван", "Иванов", "Иванович", "АДМИН", adminDtoResponse);
        }
    }
}
