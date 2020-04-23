package net.thumbtack.onlineshop;

import net.thumbtack.onlineshop.dto.response.AdminDtoResponse;
import net.thumbtack.onlineshop.dto.response.ClientDtoResponse;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static net.thumbtack.onlineshop.exception.AppErrorCode.SUCCESS;

public class AccountControllerTest extends UserTestBase {

    @Test
    public void testGetInfoAboutCurUser() throws IOException {
        ResponseEntity registerClient = createAndRegisterClient("Иван", "Иванов", "Иванович", "ivan@mail.ru", "Пушкина 39", "+7-923-697-90-06", "loginOne", "Password1!", SUCCESS);
        ResponseEntity registerAdmin = createAndRegisterAdmin("Иван", "Иванов", "Иванович", "АДМИН", "loginTwo", "Password1!", SUCCESS);

        ClientDtoResponse client = (ClientDtoResponse) registerClient.getBody();
        AdminDtoResponse admin = (AdminDtoResponse) registerAdmin.getBody();

        ClientDtoResponse clientDtoResponse = (ClientDtoResponse) getInfoAboutCurUser(registerClient.getHeaders().getFirst(HttpHeaders.SET_COOKIE), registerClient, SUCCESS).getBody();
        AdminDtoResponse adminDtoResponse = (AdminDtoResponse) getInfoAboutCurUser(registerAdmin.getHeaders().getFirst(HttpHeaders.SET_COOKIE), registerAdmin, SUCCESS).getBody();

        checkClientFields(client.getFirstName(), client.getLastName(), client.getPatronymic(), client.getEmail(), client.getAddress(), client.getPhone(), clientDtoResponse);
        checkAdminFields(admin.getFirstName(), admin.getLastName(), admin.getPatronymic(), admin.getPosition(), adminDtoResponse);
    }
}
