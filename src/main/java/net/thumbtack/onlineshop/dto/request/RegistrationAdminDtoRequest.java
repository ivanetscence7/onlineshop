package net.thumbtack.onlineshop.dto.request;


import net.thumbtack.onlineshop.validation.constraint.LoginConstraint;

public class RegistrationAdminDtoRequest{

    private String firstName;
    private String lastName;
    private String patronymic;
    private String position;
    @LoginConstraint
    private String login;
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getPosition() {
        return position;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public RegistrationAdminDtoRequest() {

    }

    public RegistrationAdminDtoRequest(String firstName, String lastName, String patronymic, String position, String login, String password) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.position = position;
        this.login = login;
        this.password = password;
    }
}
