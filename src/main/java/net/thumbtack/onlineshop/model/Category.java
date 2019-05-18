package net.thumbtack.onlineshop.model;

import java.util.List;
import java.util.Objects;

public class Category {

    private Integer id;
    private String name;
    private Category parent;
    private List<Category> subCategories;


    public Category() {
    }

    public Category(String name, Category parent) {
        this.id = 0;
        this.name = name;
        this.parent = parent;
    }

    public Category(Integer id, String name, Category parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    public Category(String name) {
        this.id = 0;
        this.name = name;
    }

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(getId(), category.getId()) &&
                Objects.equals(getName(), category.getName()) &&
                Objects.equals(getParent(), category.getParent()) &&
                Objects.equals(getSubCategories(), category.getSubCategories());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getName(), getParent(), getSubCategories());
    }
}
