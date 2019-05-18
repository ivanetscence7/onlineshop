package net.thumbtack.onlineshop.dto.request;

import net.thumbtack.onlineshop.model.Product;

import java.util.List;

public class PurchasesFromBasketDtoRequest {

    private int id;
    private String name;
    private int price;
    private int count;

    public PurchasesFromBasketDtoRequest() {
    }

    public PurchasesFromBasketDtoRequest(int id, String name, int price, int count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
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
}
