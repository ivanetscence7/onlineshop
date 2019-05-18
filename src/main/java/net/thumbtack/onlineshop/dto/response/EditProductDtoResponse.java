package net.thumbtack.onlineshop.dto.response;

import net.thumbtack.onlineshop.model.Category;

import java.util.List;

public class EditProductDtoResponse {
    private Integer id;
    private String name;
    private Integer price;
    private Integer count;
    private List<Category> categories;

    public EditProductDtoResponse(Integer id, String name, Integer price, Integer count, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
        this.categories = categories;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getCount() {
        return count;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
