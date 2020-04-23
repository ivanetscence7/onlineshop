package net.thumbtack.onlineshop.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

public class ProductDtoRequest {

    @NotBlank(message = "{product.name.NotBlank.message}")
    private String name;
    @Positive(message = "{product.price.Positive.message}")
    private int price;
    @PositiveOrZero(message = "{product.count.PositiveOrZero.message}")
    private int count;
    private List<Integer> categories;

    public ProductDtoRequest() {
    }

    public ProductDtoRequest(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public ProductDtoRequest(String name, int price, int count) {
        this(name, price);
        this.count = count;
    }

    public ProductDtoRequest(String name, int price, int count, List<Integer> categories) {
        this(name, price, count);
        if(categories==null){
            this.categories = new ArrayList<>();
        }
        this.categories = categories;
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
