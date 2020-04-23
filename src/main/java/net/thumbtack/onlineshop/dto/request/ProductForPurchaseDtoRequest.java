package net.thumbtack.onlineshop.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class ProductForPurchaseDtoRequest {

    private int id;
    @NotBlank(message = "{product.name.NotBlank.message}")
    private String name;
    @Positive(message = "{product.price.Positive.message}")
    private int price;
    @PositiveOrZero(message = "{product.count.PositiveOrZero.message}")
    private int count;

    public ProductForPurchaseDtoRequest() {

    }

    public ProductForPurchaseDtoRequest(int id, String name, int price, int count) {
        setId(id);
        setName(name);
        setPrice(price);
        setCount(count);
    }

    public int getId() {
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

    public void setId(int id) {
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

}
