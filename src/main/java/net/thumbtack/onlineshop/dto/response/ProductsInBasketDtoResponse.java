package net.thumbtack.onlineshop.dto.response;

import java.util.List;

public class ProductsInBasketDtoResponse {

    List<ProductDtoResponse> products;

    public ProductsInBasketDtoResponse() {
    }

    public ProductsInBasketDtoResponse(List<ProductDtoResponse> products) {
        this.products = products;
    }

    public List<ProductDtoResponse> getProducts() {
        return products;
    }
}

