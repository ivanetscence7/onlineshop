package net.thumbtack.onlineshop.dto.request;

import net.thumbtack.onlineshop.validation.constraint.PhoneConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ClientDtoRequest  extends UserDtoRequest {

    @NotBlank(message = "{user.email.NotBlank.message}")
    @Email(message = "{user.email.Email.message}")
    private String email;
    @NotBlank(message = "{user.address.NotBlank.message}")
    private String address;
    @NotBlank(message = "{user.phone.NotBlank.message}")
    @PhoneConstraint(message = "{user.phone.PhoneConstraint.message}")
    private String phone;

    public ClientDtoRequest() {
    }

    public ClientDtoRequest(String firstName, String lastName, String patronymic, String login, String password, String email, String address, String phone) {
        super(firstName, lastName, patronymic, login, password);
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public ClientDtoRequest(String firstName, String lastName, String login, String password, String email, String address, String phone) {
        super(firstName, lastName, login, password);
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}