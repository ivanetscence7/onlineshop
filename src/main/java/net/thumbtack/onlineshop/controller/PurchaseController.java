package net.thumbtack.onlineshop.controller;

import net.thumbtack.onlineshop.config.AppProperties;
import net.thumbtack.onlineshop.dto.request.ProductForPurchaseDtoRequest;
import net.thumbtack.onlineshop.dto.response.ProductDtoResponse;
import net.thumbtack.onlineshop.dto.response.PurchasesFromBasketDtoResponse;
import net.thumbtack.onlineshop.dto.response.StatementDtoResponse;
import net.thumbtack.onlineshop.exception.AppException;
import net.thumbtack.onlineshop.exception.ServiceException;
import net.thumbtack.onlineshop.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PurchaseController {
    private PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping(value = "/purchases", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductDtoResponse purchaseProduct(@Valid @RequestBody ProductForPurchaseDtoRequest request,
                                              @CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) throws ServiceException, AppException {
        return purchaseService.purchaseProduct(request, cookie.getValue());
    }

    @PostMapping(value = "/purchases/baskets", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PurchasesFromBasketDtoResponse purchaseProductsFromBasket(@Valid @RequestBody List<ProductForPurchaseDtoRequest> request,
                                                                     @CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) throws ServiceException {
        return purchaseService.purchaseProductsFromBasket(request, cookie.getValue());
    }

    @GetMapping(value = "/purchases", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatementDtoResponse getStatementByParam(@RequestParam(required = false, value = "category") List<Integer> category,
                                                    @RequestParam(required = false, value = "product") List<Integer> product,
                                                    @RequestParam(required = false, value = "clientId") List<Integer> clientId,
                                                    @RequestParam(required = false, value = "limit") Integer limit,
                                                    @RequestParam(required = false, value = "offset") Integer offset,
                                                    @RequestParam(required = false, value = "criterion") String criterion,
                                                    @RequestParam(required = false, value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                    @RequestParam(required = false, value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                    @CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) {
        return purchaseService.getStatementByParams(category, product, clientId, limit, offset, startDate, endDate, criterion, cookie.getValue());
    }
}
