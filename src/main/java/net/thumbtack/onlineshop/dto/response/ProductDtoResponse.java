package net.thumbtack.onlineshop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.thumbtack.onlineshop.model.Category;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDtoResponse {
    private Integer id;
    private String name;
    private Integer price;
    private Integer count;
    private List<Integer> categories;

    public ProductDtoResponse() {
    }


    public ProductDtoResponse(Integer id, String name, Integer price, Integer count, List<Integer> categories) {
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

    public List<Integer> getCategories() {
        return categories;
    }
}
