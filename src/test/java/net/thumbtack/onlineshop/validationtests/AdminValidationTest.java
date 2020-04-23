package net.thumbtack.onlineshop.validationtests;

import net.thumbtack.onlineshop.controller.AdminController;
import net.thumbtack.onlineshop.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static net.thumbtack.onlineshop.exception.AppErrorCode.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminValidationTest extends ValidationUserTestBase {

    @MockBean
    private AdminService adminService;

    @Test
    public void testRegistrationAdminWithInvalidFirstName() throws Exception {
        createAndRegisterInvalidAdmin("Ivan", "Иванов", "Иванович", "АДМИН", "login1", "Password1!", WRONG_FIRSTNAME);
    }

    @Test
    public void testRegistrationAdminWithInvalidLastName() throws Exception {
        createAndRegisterInvalidAdmin("Иван", "Ivanow", "Иванович", "АДМИН", "login2", "Password1!", WRONG_LASTNAME);
    }

    @Test
    public void testRegistrationAdminWithInvalidPatronymic() throws Exception {
        createAndRegisterInvalidAdmin("Иван", "Иванов", "Ivanowich", "АДМИН", "login3", "Password1!", WRONG_PATRONYMIC);
    }

    @Test
    public void testRegistrationAdminWithInvalidLogin() throws Exception {
        createAndRegisterInvalidAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login[][!@$@#$!$@", "Password1!", WRONG_LOGIN);
    }

    @Test
    public void testRegistrationAdminWithInvalidPassword() throws Exception {
        createAndRegisterInvalidAdmin("Иван", "Иванов", "Иванович", "АДМИН", "login4", "Password", WRONG_PASSWORD);
    }

    @Test
    public void testRegistrationAdminWithInvalidPosition() throws Exception {
        createAndRegisterInvalidAdmin("Иван", "Иванов", "Иванович", "", "login5", "Password1!", WRONG_POSITION);
    }

    @Test
    public void testRegistrationAdminWithInvalidFullName() throws Exception {
        createAndRegisterInvalidAdmin("Ivan", "Ivanow", "Ivanowich", "АДМИН", "login5", "Password1!",
                WRONG_FIRSTNAME, WRONG_LASTNAME, WRONG_PATRONYMIC);
    }

    @Test
    public void testRegistrationAdminWithEmptyFullName() throws Exception {
        createAndRegisterInvalidAdmin("Ivan", "Ivanow", "Ivanowich", "АДМИН", "login5", "Password1!",
                WRONG_FIRSTNAME, WRONG_LASTNAME, WRONG_PATRONYMIC);
    }

    @Test
    public void testRegistrationAdminWithNullFullName() throws Exception {
        createAndRegisterInvalidAdmin(null, null, null, "АДМИН", "login5", "Password1!",
                WRONG_FIRSTNAME, WRONG_LASTNAME);
    }


    @Test
    public void testUpdateAdminWithInvalidFirstName() throws Exception {
        updateInvalidAdmin("Ivan", "Иванов", "Иванович", "АДМИН", "Password1!","Password2!",WRONG_FIRSTNAME);
    }

    @Test
    public void testUpdateAdminWithInvalidLastName() throws Exception {
        updateInvalidAdmin("Иван", "Ivanow", "Иванович", "АДМИН", "Password1!","Password2!",WRONG_LASTNAME);
    }

    @Test
    public void testUpdateAdminWithInvalidPatronymic() throws Exception {
        updateInvalidAdmin("Иван", "Иванов", "Ivanowich", "АДМИН", "Password1!","Password2!",WRONG_PATRONYMIC);
    }

    @Test
    public void testUpdateAdminWithInvalidPosition() throws Exception {
        updateInvalidAdmin("Иван", "Иванов", "Иванович", "", "Password1!","Password2!",WRONG_POSITION);
    }

    @Test
    public void testUpdateAdminWithInvalidOldPassword() throws Exception {
        updateInvalidAdmin("Иван", "Иванов", "Иванович", "АДМИН", "Password","Password2!",WRONG_OLDPASSWORD);
    }

    @Test
    public void testUpdateAdminWithInvalidNewPassword() throws Exception {
        updateInvalidAdmin("Иван", "Иванов", "Иванович", "АДМИН", "Password1!","Password",WRONG_NEWPASSWORD);
    }

    @Test
    public void testUpdateAdminWithInvalidFullName() throws Exception {
        updateInvalidAdmin("Ivan", "Ivanow", "Ivanowich", "АДМИН", "Password1!","Password2!",
                WRONG_FIRSTNAME, WRONG_LASTNAME, WRONG_PATRONYMIC);
    }

    @Test
    public void testUpdateAdminWithEmptyFullName() throws Exception {
        updateInvalidAdmin("", "", "", "АДМИН", "Password1!","Password1!",
                WRONG_FIRSTNAME, WRONG_LASTNAME, WRONG_FIRSTNAME, WRONG_LASTNAME, WRONG_PATRONYMIC);
    }

    @Test
    public void testUpdateAdminWithNullFullName() throws Exception {
        updateInvalidAdmin(null, null, null, "АДМИН", "Password1!","Password1!",
                WRONG_FIRSTNAME, WRONG_LASTNAME);
    }
 }
