package net.thumbtack.onlineshop.dto.request;

import net.thumbtack.onlineshop.validation.constraint.PhoneConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UpdateClientDtoRequest extends UpdateUserDtoRequest {


    @NotBlank(message = "{user.email.NotBlank.message}")
    @Email
    private String email;
    @NotBlank(message = "{user.address.NotBlank.message}")
    private String address;
    @NotBlank(message = "{user.phone.NotBlank.message}")
    @PhoneConstraint(message = "{user.phone.PhoneConstraint.message}")
    private String phone;

    public UpdateClientDtoRequest() {
    }

    public UpdateClientDtoRequest(String firstName, String lastName, String patronymic, String oldPassword, String newPassword, String email, String address, String phone) {
        super(firstName, lastName, patronymic,  oldPassword, newPassword);
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
