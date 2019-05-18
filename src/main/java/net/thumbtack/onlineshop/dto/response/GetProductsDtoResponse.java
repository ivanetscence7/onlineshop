package net.thumbtack.onlineshop.dto.response;

import net.thumbtack.onlineshop.model.Category;

import java.util.List;

public class GetProductsDtoResponse {

    private Integer id;
    private String name;
    private Integer count;
    private Integer price;
    private List<Category> categories;

    public GetProductsDtoResponse(Integer id, String name, Integer count, Integer price, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.price = price;
        this.categories = categories;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getPrice() {
        return price;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
