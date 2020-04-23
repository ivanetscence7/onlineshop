package net.thumbtack.onlineshop.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDtoResponse {

    private Integer id;
    private String name;
    private int price;
    private int count;
    @JsonUnwrapped
    private List<Integer> categories;

    public ProductDtoResponse() {
    }

    public ProductDtoResponse(Integer id, String name, Integer price, Integer count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public ProductDtoResponse(Integer id, String name, Integer price, Integer count, List<Integer> categories) {
        this(id, name, price, count);
        this.categories = categories;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }
}