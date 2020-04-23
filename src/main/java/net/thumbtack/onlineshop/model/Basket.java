package net.thumbtack.onlineshop.model;

import java.util.List;
import java.util.Objects;

public class Basket {

    private int id;
    private List<Product> products;

    public Basket() {
    }

    public Basket(int id, List<Product> products) {
        this.id = id;
        this.products = products;
    }

    public Basket(List<Product> products) {
        this.id = 0;
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return getId() == basket.getId() &&
                Objects.equals(getProducts(), basket.getProducts());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getProducts());
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", products=" + products +
                '}';
    }
}
