package net.thumbtack.onlineshop.dto.response;


import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("admin")
public class AdminDtoResponse extends UserDtoResponse {

    private String position;

    public AdminDtoResponse() {
    }

    public AdminDtoResponse(Integer id, String firstName, String lastName, String patronymic, String position) {
        super(id, firstName, lastName, patronymic);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}
