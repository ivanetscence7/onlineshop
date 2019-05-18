package net.thumbtack.onlineshop.model;

import java.util.Objects;

public class Client extends User{

    private String email;
    private String address;
    private Integer phone;
    private Deposit deposit;

    public Client() {
    }

    public Client(String email, String address, Integer phone, Deposit deposit) {
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.deposit = deposit;
    }

    public Client(String firstName, String lastName, String patronymic, String login, String password, UserType userType, String email, String address, Integer phone, Deposit deposit) {
        super(firstName, lastName, patronymic, login, password, userType);
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.deposit = deposit;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public void setDeposit(Deposit deposit) {
        this.deposit = deposit;
    }

    public String getEmail() {
        return email;
    }


    public String getAddress() {
        return address;
    }


    public Deposit getDeposit() {
        return deposit;
    }

    public Integer getPhone() {
        return phone;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return Objects.equals(getEmail(), client.getEmail()) &&
                Objects.equals(getAddress(), client.getAddress()) &&
                Objects.equals(getPhone(), client.getPhone()) &&
                Objects.equals(getDeposit(), client.getDeposit());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getEmail(), getAddress(), getPhone(), getDeposit());
    }
}
