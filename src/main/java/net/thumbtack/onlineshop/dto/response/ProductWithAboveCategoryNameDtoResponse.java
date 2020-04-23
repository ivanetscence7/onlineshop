package net.thumbtack.onlineshop.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.*;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductWithAboveCategoryNameDtoResponse {
    private String categoryName;
    private Set<ProductDtoResponse> products;

    public ProductWithAboveCategoryNameDtoResponse() {
        this.categoryName = new String();
        this.products = new TreeSet<>();
    }

    public ProductWithAboveCategoryNameDtoResponse(String categoryName, Set<ProductDtoResponse> products) {
        this.categoryName = categoryName;
        this.products = products;
    }

    public ProductWithAboveCategoryNameDtoResponse(Set<ProductDtoResponse> products) {
        this.categoryName = new String();
        this.products = new TreeSet<>(products);
    }

    public boolean addProductDtoResponse(ProductDtoResponse productDtoResponse) {
        return products.add(productDtoResponse);
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Set<ProductDtoResponse> getProducts() {
        return products;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setProducts(Set<ProductDtoResponse> products) {
        this.products = products;
    }


}
