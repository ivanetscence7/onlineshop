package net.thumbtack.onlineshop.dto.request;

import net.thumbtack.onlineshop.model.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductDtoRequest {
    @NotBlank
    private String name;
    @NotNull
    private Integer price;
    private Integer count;
    private List<Integer> categories;

    public ProductDtoRequest() {
    }

    public ProductDtoRequest(String name, Integer price, Integer count, List<Integer> categories) {
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

    public List<Integer> getCategories() {
        return categories;
    }
}
