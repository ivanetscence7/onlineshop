package net.thumbtack.onlineshop.dto.response;

import java.util.List;

public class PurchasesFromBasketDtoResponse {

    private List<ProductDtoResponse> bought;
    private List<ProductDtoResponse> remaining;

    public PurchasesFromBasketDtoResponse() {
    }

    public PurchasesFromBasketDtoResponse(List<ProductDtoResponse> bought, List<ProductDtoResponse> remaining) {
        this.bought = bought;
        this.remaining = remaining;
    }

    public List<ProductDtoResponse> getBought() {
        return bought;
    }

    public List<ProductDtoResponse> getRemaining() {
        return remaining;
    }
}
