package net.thumbtack.onlineshop.dto.request;

import net.thumbtack.onlineshop.model.Category;

import java.util.List;

public class EditProductDtoRequest {

    private String name;
    private Integer price;
    private Integer count;
    private List<Category> categories;

    public EditProductDtoRequest(String name, Integer price, Integer count, List<Category> categories) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.categories = categories;
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
