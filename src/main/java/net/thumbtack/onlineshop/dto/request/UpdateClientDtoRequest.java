package net.thumbtack.onlineshop.dto.request;

public class UpdateClientDtoRequest {

    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private String address;
    private Integer phone;
    private String oldPassword;
    private String newPassword;

    public UpdateClientDtoRequest() {
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

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Integer getPhone() {
        return phone;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public UpdateClientDtoRequest(String firstName, String lastName, String patronymic, String email, String address, Integer phone, String oldPassword, String newPassword) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
