package net.thumbtack.onlineshop.dto.response;

public class InputClientDtoResponse extends InputUserResponse{

    private String email;
    private String address;
    private Integer phone;
    private Integer deposit;

    public InputClientDtoResponse() {
    }

    public InputClientDtoResponse(Integer id, String firstName, String lastName, String patronymic, String email, String address, Integer phone, Integer deposit) {
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

    public Integer getPhone() {
        return phone;
    }

    public Integer getDeposit() {
        return deposit;
    }
}
