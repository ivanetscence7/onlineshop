package net.thumbtack.onlineshop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductWithCategoryNameDtoResponse {

    private Integer id;
    private String name;
    private Integer price;
    private Integer count;
    private List<String> categories;

    public ProductWithCategoryNameDtoResponse() {
    }

    public ProductWithCategoryNameDtoResponse(Integer id, String name, Integer price, Integer count, List<String> categories) {
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

    public List<String> getCategories() {
        return categories;
    }
}
