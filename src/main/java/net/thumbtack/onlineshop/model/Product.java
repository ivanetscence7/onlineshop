package net.thumbtack.onlineshop.model;

import java.util.List;
import java.util.Objects;

public class Product implements Comparable<Product> {

    private int id;
    private String name;
    private int price;
    private int count;
    private List<Category> categories;
    private Integer version;

    public Product() {
    }

    public Product(int id, String name, int price, int count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public Product(int id, String name, int price, int count, List<Category> categories) {
        this(id, name, price, count);
        this.categories = categories;
    }

    public Product(String name, int price, int count) {
       this(0,name,price,count);
    }

    public Product(int id, String name, int price, int count, Integer version) {
        this(id, name, price, count);
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return getId() == product.getId() &&
                getPrice() == product.getPrice() &&
                getCount() == product.getCount() &&
                Objects.equals(getName(), product.getName()) &&
                Objects.equals(getCategories(), product.getCategories()) &&
                Objects.equals(getVersion(), product.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice(), getCount(), getCategories(), getVersion());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", categories=" + categories +
                ", version=" + version +
                '}';
    }

    @Override
    public int compareTo(Product o) {
        return this.name.compareTo(o.getName());
    }
}


