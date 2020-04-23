package net.thumbtack.onlineshop.dto.request;

import net.thumbtack.onlineshop.validation.constraint.NameConstraint;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

public class UpdateProductDtoRequest {

    @NameConstraint
    private String name;
    @Positive
    private Integer price;
    @PositiveOrZero
    private Integer count;
    private List<Integer> categories;

    public UpdateProductDtoRequest() {
    }

    public UpdateProductDtoRequest(String name, Integer price, Integer count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public UpdateProductDtoRequest(String name, Integer price, Integer count, List<Integer> categories) {
        this(name, price, count);
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
