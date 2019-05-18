package net.thumbtack.onlineshop.dto.response;

import net.thumbtack.onlineshop.model.Product;

import java.util.List;

public class CompositionBasketDtoResponse {

    List<Product> list;

    public CompositionBasketDtoResponse(List<Product> list) {
        this.list = list;
    }

    public List<Product> getList() {
        return list;
    }

    
}
