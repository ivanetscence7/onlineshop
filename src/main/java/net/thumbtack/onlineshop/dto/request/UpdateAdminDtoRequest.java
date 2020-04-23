package net.thumbtack.onlineshop.dto.request;

import javax.validation.constraints.NotBlank;

public class UpdateAdminDtoRequest extends UpdateUserDtoRequest {

    @NotBlank(message = "{user.position.NotBlank.message}")
    private String position;

    public UpdateAdminDtoRequest() {
    }

    public UpdateAdminDtoRequest(@NotBlank String position) {
        this.position = position;
    }

    public UpdateAdminDtoRequest(String firstName, String lastName, String patronymic, String oldPassword, String newPassword, String position) {
        super(firstName, lastName, patronymic, oldPassword, newPassword);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

}
