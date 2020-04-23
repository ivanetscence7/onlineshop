package net.thumbtack.onlineshop.model;

import java.util.List;
import java.util.Objects;

public class Category  implements Comparable<Category>{

    private Integer id;
    private String name;
    private Category parent;
    private List<Category> subCategories;
    private List<Product> products;

    public Category() {
    }

    public Category(List<Product> products) {
        this.products = products;
    }

    public Category(Integer id, String name, Category parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    public Category(String name, Category parent) {
       this(0,name,parent);
    }

    public Category(String name) {
        this.id = 0;
        this.name = name;
    }

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public String getName() {
        return name;
    }

    public Category getParent() {
        return parent;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }


    @Override
    public int compareTo(Category o) {
        return name.compareTo(o.getName());
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent=" + parent +
                ", subCategories=" + subCategories +
                ", products=" + products +
                '}';
    }
}