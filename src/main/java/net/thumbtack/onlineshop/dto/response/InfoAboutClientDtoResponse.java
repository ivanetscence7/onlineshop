package net.thumbtack.onlineshop.dto.response;

import net.thumbtack.onlineshop.model.UserType;

public class InfoAboutClientDtoResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private String address;
    private Integer phone;
    private UserType userType;

    public InfoAboutClientDtoResponse() {
    }

    public InfoAboutClientDtoResponse(Integer id, String firstName, String lastName, String patronymic, String email, String address, Integer phone, UserType userType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.userType = userType;
    }

    public Integer getId() {
        return id;
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

    public UserType getUserType() {
        return userType;
    }
}
