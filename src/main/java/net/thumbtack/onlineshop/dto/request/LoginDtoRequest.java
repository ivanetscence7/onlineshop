package net.thumbtack.onlineshop.dto.request;

import net.thumbtack.onlineshop.validation.constraint.LoginConstraint;

public class LoginDtoRequest {

    private String login;
    private String password;

    public LoginDtoRequest() {
    }

    public LoginDtoRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
