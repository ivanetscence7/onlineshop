package net.thumbtack.onlineshop.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AdminDtoRequest extends UserDtoRequest {

    @NotBlank(message = "{user.position.NotBlank.message}")
    private String position;

    public AdminDtoRequest() {
    }

    public AdminDtoRequest(String position) {
        this.position = position;
    }

    public AdminDtoRequest(String firstName, String lastName, String login, String password, String position) {
        super(firstName, lastName, login, password);
        this.position = position;
    }

    public AdminDtoRequest(String firstName, String lastName, String patronymic, String login, String password, String position) {
        super(firstName, lastName, patronymic, login, password);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}
