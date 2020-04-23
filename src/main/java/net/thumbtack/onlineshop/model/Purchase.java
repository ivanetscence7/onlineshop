package net.thumbtack.onlineshop.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Purchase {

    private int id;
    private Date date;
    private int amount;
    private List<Product> products;

    public Purchase() {
    }

    public Purchase(int id, Date date, int amount) {
        this.id = id;
        this.date = date;
        this.amount = amount;
    }

    public Purchase(Date date, int amount) {
        this(0, date, amount);
    }

    public Purchase(int id, Date date, int amount, List<Product> products) {
        this(id,date,amount);
        this.products = products;
    }

    public Purchase(Date date, int amount, List<Product> products) {
        this(0, date, amount, products);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return getId() == purchase.getId() &&
                getAmount() == purchase.getAmount() &&
                Objects.equals(getDate(), purchase.getDate()) &&
                Objects.equals(getProducts(), purchase.getProducts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDate(), getAmount(), getProducts());
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", date=" + date +
                ", amount=" + amount +
                ", products=" + products +
                '}';
    }
}
