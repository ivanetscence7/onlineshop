package net.thumbtack.onlineshop.model;

import java.util.List;
import java.util.Objects;

public class Client extends User {

    private String email;
    private String address;
    private String phone;
    private Deposit deposit = new Deposit();
    private Basket basket = new Basket();
    private List<Purchase> purchases;

    public Client() {
    }

    public Client(String email, String address, String phone) {
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public Client(String firstName, String lastName, String patronymic, String login, String password, UserType userType, String email, String address, String phone) {
        super(firstName, lastName, patronymic, login, password, userType);
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Client(String email, String address, String phone, Deposit deposit, Basket basket, List<Purchase> purchases) {
        this(email,phone,address);
        this.deposit = deposit;
        this.basket = basket;
        this.purchases = purchases;
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

    public String getPhone() {
        return phone;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
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
                Objects.equals(getDeposit(), client.getDeposit()) &&
                Objects.equals(getBasket(), client.getBasket()) &&
                Objects.equals(getPurchases(), client.getPurchases());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEmail(), getAddress(), getPhone(), getDeposit(), getBasket(), getPurchases());
    }

    @Override
    public String toString() {
        return "Client{" +
                "email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", deposit=" + deposit +
                ", basket=" + basket +
                ", purchases=" + purchases +
                '}';
    }
}
