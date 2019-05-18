package net.thumbtack.onlineshop.dto.response;

public class InputUserResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String patronymic;

    public InputUserResponse() {
    }

    public InputUserResponse(Integer id, String firstName, String lastName, String patronymic) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
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
}
