package net.thumbtack.onlineshop.validationtests;

import net.thumbtack.onlineshop.controller.ClientController;
import net.thumbtack.onlineshop.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static net.thumbtack.onlineshop.exception.AppErrorCode.*;
import static net.thumbtack.onlineshop.exception.AppErrorCode.WRONG_LASTNAME;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientController.class)
public class ClientValidationTest extends ValidationUserTestBase {

    @MockBean
    private ClientService clientService;

    @Test
    public void testRegistrationClientWithInvalidFirstName() throws Exception {
        createAndRegisterInvalidClient("Ivan", "Иванов", "Иванович", "ivan@gmail.com",
                "Пушкина 39", "+79236979006", "login1", "Password1!", WRONG_FIRSTNAME);
    }

    @Test
    public void testRegistrationClientWithInvalidLastName() throws Exception {
        createAndRegisterInvalidClient("Иван", "Ivanow", "Иванович", "ivan@gmail.com",
                "Пушкина 39", "+79236979006", "login1", "Password1!", WRONG_LASTNAME);
    }

    @Test
    public void testRegistrationClientWithInvalidPatronymic() throws Exception {
        createAndRegisterInvalidClient("Иван", "Иванов", "Ivanovich", "ivan@gmail.com",
                "Пушкина 39", "+79236979006", "login1", "Password1!", WRONG_PATRONYMIC);
    }

    @Test
    public void testRegistrationClientWithInvalidLogin() throws Exception {
        createAndRegisterInvalidClient("Иван", "Иванов", "Иванович", "ivan@gmail.com",
                "Пушкина 39", "+79236979006", "login[][!@$@#$!$@", "Password1!", WRONG_LOGIN);
    }

    @Test
    public void testRegistrationClientWithInvalidPassword() throws Exception {
        createAndRegisterInvalidClient("Иван", "Иванов", "Иванович", "ivan@gmail.com",
                "Пушкина 39", "+79236979006", "login2", "Password", WRONG_PASSWORD);
    }

    @Test
    public void testRegistrationClientWithInvalidAddress() throws Exception {
        createAndRegisterInvalidClient("Иван", "Иванов", "Иванович", "ivan@gmail.com",
                "", "+79236979006", "login2", "Password1!", WRONG_ADDRESS);
    }

    @Test
    public void testRegistrationClientWithInvalidPhone() throws Exception {
        createAndRegisterInvalidClient("Иван", "Иванов", "Иванович", "ivan@gmail.com",
                "Пушкина 39", "464646", "login2", "Password1!", WRONG_PHONENUMBER);

        createAndRegisterInvalidClient("Иван", "Иванов", "Иванович", "ivan@gmail.com",
                "Пушкина 39", "+69236979006", "login2", "Password1!", WRONG_PHONENUMBER);

        createAndRegisterInvalidClient("Иван", "Иванов", "Иванович", "ivan@gmail.com",
                "Пушкина 39", "9236979006", "login2", "Password1!", WRONG_PHONENUMBER);
    }

    @Test
    public void testRegistrationClientWithInvalidEmail() throws Exception {
        createAndRegisterInvalidClient("Иван", "Иванов", "Иванович", "wrongemail",
                "Пушкина 39", "+79236979006", "login2", "Password1!", WRONG_EMAIL);
    }

    @Test
    public void testRegistrationClientWithInvalidFullName() throws Exception {
        createAndRegisterInvalidClient("Ivan", "Ivanow", "Ivanowich", "ivan@gmail.com",
                "Пушкина 39", "+79236979006", "login2", "Password1!", WRONG_FIRSTNAME, WRONG_LASTNAME, WRONG_PATRONYMIC);
    }

    @Test
    public void testRegistrationClientWithEmptyFirstNameAndLastName() throws Exception {
        createAndRegisterInvalidClient("", "", "", "ivan@gmail.com",
                "Пушкина 39", "+79236979006", "login2", "Password1!",
                WRONG_FIRSTNAME, WRONG_LASTNAME, WRONG_FIRSTNAME, WRONG_LASTNAME, WRONG_PATRONYMIC);
    }

    @Test
    public void testRegistrationClientWithNullFirstNameAndLastName() throws Exception {
        createAndRegisterInvalidClient(null, null, null, "ivan@gmail.com",
                "Пушкина 39", "+79236979006", "login5", "Password1!",
                WRONG_FIRSTNAME, WRONG_FIRSTNAME);
    }

    @Test
    public void testUpdateClientWithInvalidFirstName() throws Exception {
        updateInvalidClient("Petr", "Иванов", "Иванович", "ivan@gmail.com",
                "Пушкина 39", "+79236979006", "Password1!", "Password1!", WRONG_FIRSTNAME);
    }

    @Test
    public void testUpdateClientWithInvalidLastName() throws Exception {
        updateInvalidClient("Иван", "Ivanow", "Иванович", "ivan@gmail.com",
                "Пушкина 39", "+79236979006", "Password1!", "Password2!", WRONG_LASTNAME);
    }

    @Test
    public void testUpdateClientWithInvalidPatronymic() throws Exception {
        updateInvalidClient("Иван", "Иванов", "Ivanovich", "ivan@gmail.com",
                "Пушкина 39", "+79236979006", "Password1!", "Password1!", WRONG_PATRONYMIC);
    }

    @Test
    public void testUpdateClientWithInvalidEmail() throws Exception {
        updateInvalidClient("Иван", "Иванов", "Иванович", "wrongemail", "Пушкина 39",
                "+79236979006", "Password1!", "Password2!", WRONG_EMAIL);
    }

    @Test
    public void testUpdateClientWithInvalidAddress() throws Exception {
        updateInvalidClient("Иван", "Иванов", "Иванович", "ivan@gmail.com", "",
                "+79236979006", "Password1!", "Password2!", WRONG_ADDRESS);
    }

    @Test
    public void testUpdateClientWithInvalidPhone() throws Exception {
        updateInvalidClient("Иван", "Иванов", "Иванович", "ivan@gmail.com", "Пушкина 39",
                "4464646", "Password1!", "Password2!", WRONG_PHONENUMBER);
    }

    @Test
    public void testUpdateClientWithInvalidOldPassword() throws Exception {
        updateInvalidClient("Иван", "Иванов", "Иванович", "ivan@gmail.com", "Пушкина 39",
                "+79236979006", "Password", "Password2!", WRONG_OLDPASSWORD);
    }

    @Test
    public void testUpdateClientWithInvalidNewPassword() throws Exception {
        updateInvalidClient("Иван", "Иванов", "Иванович", "ivan@gmail.com", "Пушкина 39",
                "+79236979006", "Password1!", "Password", WRONG_NEWPASSWORD);
    }

    @Test
    public void testUpdateClientWithInvalidFullName() throws Exception {
        updateInvalidClient("Ivan", "Ivanov", "Ivanovich", "ivan@gmail.com", "Пушкина 39",
                "+79236979006", "Password1!", "Password2!", WRONG_FIRSTNAME, WRONG_LASTNAME, WRONG_PATRONYMIC);
    }

    @Test
    public void testUpdateClientWithEmptyFullName() throws Exception {
        updateInvalidClient("", "", "", "ivan@gmail.com", "Пушкина 39",
                "+79236979006", "Password1!", "Password2!", WRONG_FIRSTNAME,  WRONG_LASTNAME,  WRONG_FIRSTNAME,  WRONG_LASTNAME, WRONG_PATRONYMIC);
    }

    @Test
    public void testUpdateClientWithNullFullName() throws Exception {
        updateInvalidClient(null, null, null, "ivan@gmail.com", "Пушкина 39",
                "+79236979006", "Password1!", "Password2!", WRONG_FIRSTNAME, WRONG_LASTNAME);
    }
}
