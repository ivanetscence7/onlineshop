package net.thumbtack.onlineshop.dto.request;

public class RegistrationClientDtoRequest{

    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private String address;
    // private String phone; //
    private Integer phone;
    private String login;
    private String password;

    public RegistrationClientDtoRequest() {
    }

    public RegistrationClientDtoRequest(String firstName, String lastName, String patronymic, String email, String address, Integer phone, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.login = login;
        this.password = password;
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

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
