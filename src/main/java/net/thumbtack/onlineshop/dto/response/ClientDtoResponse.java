package net.thumbtack.onlineshop.dto.response;


import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("client")
public class ClientDtoResponse extends UserDtoResponse {

    private String email;
    private String address;
    private String phone;
    private Integer deposit;

    public ClientDtoResponse() {
    }

    public ClientDtoResponse(Integer id, String firstName, String lastName, String patronymic, String email, String address, String phone) {
        super(id, firstName, lastName, patronymic);
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public ClientDtoResponse(Integer id, String firstName, String lastName, String patronymic, String email, String address, String phone, Integer deposit) {
        super(id, firstName, lastName, patronymic);
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.deposit = deposit;
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

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }
}

