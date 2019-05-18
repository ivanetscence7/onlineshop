package net.thumbtack.onlineshop.dto.response;

import net.thumbtack.onlineshop.model.Product;

import java.util.List;

public class PurchasesFromBasketDtoResponse {

    List<Product> bought;
    List<Product> remaining;

    public PurchasesFromBasketDtoResponse(List<Product> bought, List<Product> remaining) {
        this.bought = bought;
        this.remaining = remaining;
    }

    public PurchasesFromBasketDtoResponse(List<Product> bought) {
        this.bought = bought;
    }



    public List<Product> getBought() {
        return bought;
    }

    public List<Product> getRemaining() {
        return remaining;
    }
}
