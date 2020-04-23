package net.thumbtack.onlineshop.controller;

import net.thumbtack.onlineshop.config.AppProperties;
import net.thumbtack.onlineshop.dto.request.ProductDtoRequest;
import net.thumbtack.onlineshop.dto.request.UpdateProductDtoRequest;
import net.thumbtack.onlineshop.dto.response.AllProductsDtoResponse;
import net.thumbtack.onlineshop.dto.response.EmptyResponse;
import net.thumbtack.onlineshop.dto.response.ProductDtoResponse;
import net.thumbtack.onlineshop.dto.response.ProductWithCategoryNameDtoResponse;
import net.thumbtack.onlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductDtoResponse addProduct(@Valid @RequestBody ProductDtoRequest request,
                                         @CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) {
        return productService.addProduct(request, cookie.getValue());
    }

    @PutMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductDtoResponse updateProduct(@PathVariable("id") int productId, @Valid @RequestBody UpdateProductDtoRequest request,
                                            @CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) {
        return productService.updateProduct(productId, cookie.getValue(), request);
    }

    @DeleteMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public EmptyResponse deleteProduct(@PathVariable("id") int productId, @CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) {
        return productService.deleteProduct(productId, cookie.getValue());
    }

    @GetMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductWithCategoryNameDtoResponse getInfoAboutProduct(@PathVariable("id") int productId, @CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) {
        return productService.getInfoAboutProduct(productId, cookie.getValue());
    }

    @GetMapping(value = "/products", produces = APPLICATION_JSON_VALUE)
    public AllProductsDtoResponse getProductByParams(@RequestParam(required = false, value = "category") List<Integer> category,
                                                 @RequestParam(required = false, defaultValue = "product") String order,
                                                 @CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) {
        return productService.getProductsByParams(category, order, cookie.getValue());
    }
}
