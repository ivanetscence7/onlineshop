package net.thumbtack.onlineshop.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClientDtoResponse.class, name = "client"),
        @JsonSubTypes.Type(value = AdminDtoResponse.class, name = "admin")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDtoResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String patronymic;

    public UserDtoResponse() {
    }

    public UserDtoResponse(Integer id, String firstName, String lastName, String patronymic) {
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

