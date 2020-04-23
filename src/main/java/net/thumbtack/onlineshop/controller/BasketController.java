package net.thumbtack.onlineshop.controller;


import net.thumbtack.onlineshop.config.AppProperties;
import net.thumbtack.onlineshop.dto.request.ProductForPurchaseDtoRequest;
import net.thumbtack.onlineshop.dto.response.EmptyResponse;
import net.thumbtack.onlineshop.dto.response.ProductsInBasketDtoResponse;
import net.thumbtack.onlineshop.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class BasketController {

    private BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping(value = "/baskets", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductsInBasketDtoResponse addProductToBasket(@Valid @RequestBody ProductForPurchaseDtoRequest request,
                                                          @CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) {
        return basketService.addProductToBasket(request, cookie.getValue());
    }

    @DeleteMapping(value = "/baskets/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public EmptyResponse deleteProductFromBasket(@PathVariable("id") int productId, @CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) {
        return basketService.deleteProductFromBasket(productId, cookie.getValue());
    }

    @PutMapping(value = "/baskets", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductsInBasketDtoResponse updateProductCountInBasket(@Valid @RequestBody ProductForPurchaseDtoRequest request,
                                                                  @CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) {
        return basketService.updateProductCountInBasket(cookie.getValue(), request);
    }

    @GetMapping(value = "/baskets", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductsInBasketDtoResponse getBasketContent(@CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) {
        return basketService.getBasketContent(cookie.getValue());
    }

}

