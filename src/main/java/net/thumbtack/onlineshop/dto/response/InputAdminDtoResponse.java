package net.thumbtack.onlineshop.dto.response;

import net.thumbtack.onlineshop.model.User;

public class InputAdminDtoResponse extends InputUserResponse {

    private String position;

    public InputAdminDtoResponse() {
    }

    public InputAdminDtoResponse(Integer id, String firstName, String lastName, String patronymic, String position) {
        super(id, firstName, lastName, patronymic);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}
