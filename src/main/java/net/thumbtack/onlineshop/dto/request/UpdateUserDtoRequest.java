package net.thumbtack.onlineshop.dto.request;

import net.thumbtack.onlineshop.validation.constraint.NameConstraint;
import net.thumbtack.onlineshop.validation.constraint.PasswordConstraint;

import javax.validation.constraints.NotBlank;

public class UpdateUserDtoRequest {

    @NotBlank(message = "{user.firstName.NotBlank.message}")
    @NameConstraint(message = "{user.firstName.NameConstraint.message}")
    private String firstName;
    @NotBlank(message = "{user.lastName.NotBlank.message}")
    @NameConstraint(message = "{user.lastName.NameConstraint.message}")
    private String lastName;
    @NameConstraint(message = "{user.patronymic.NameConstraint.message}")
    private String patronymic;
    @NotBlank(message = "{user.password.NotBlank.message}")
    @PasswordConstraint(message = "{user.password.PasswordConstraint.message}")
    private String oldPassword;
    @NotBlank(message = "{user.password.NotBlank.message}")
    @PasswordConstraint(message = "{user.password.PasswordConstraint.message}")
    private String newPassword;

    public UpdateUserDtoRequest() {
    }

    public UpdateUserDtoRequest(String firstName, String lastName, String patronymic, String oldPassword, String newPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
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

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
