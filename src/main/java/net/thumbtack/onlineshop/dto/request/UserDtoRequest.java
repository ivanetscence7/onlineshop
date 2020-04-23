package net.thumbtack.onlineshop.dto.request;

import net.thumbtack.onlineshop.validation.constraint.LoginConstraint;
import net.thumbtack.onlineshop.validation.constraint.NameConstraint;
import net.thumbtack.onlineshop.validation.constraint.PasswordConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public abstract class UserDtoRequest {

    @NotBlank(message = "{user.firstName.NotBlank.message}")
    @NameConstraint(message = "{user.firstName.NameConstraint.message}")
    private String firstName;
    @NotBlank(message = "{user.lastName.NotBlank.message}")
    @NameConstraint(message = "{user.lastName.NameConstraint.message}")
    private String lastName;
    @NameConstraint(message = "{user.patronymic.NameConstraint.message}")
    private String patronymic;
    @NotBlank(message = "{user.login.NotBlank.message}")
    @LoginConstraint(message = "{user.login.LoginConstraint.message}")
    private String login;
    @NotBlank(message = "{user.password.NotBlank.message}")
    @PasswordConstraint(message = "{user.password.PasswordConstraint.message}")
    private String password;

    public UserDtoRequest() {
    }

    public UserDtoRequest(String firstName, String lastName, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public UserDtoRequest(String firstName, String lastName, String patronymic, String login, String password) {
        this(firstName, lastName, login, password);
        this.patronymic = patronymic;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
