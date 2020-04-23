package net.thumbtack.onlineshop.controller;

import net.thumbtack.onlineshop.config.AppProperties;
import net.thumbtack.onlineshop.dto.request.DepositDtoRequest;
import net.thumbtack.onlineshop.dto.response.ClientDtoResponse;
import net.thumbtack.onlineshop.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class DepositController {

    private DepositService depositService;

    @Autowired
    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PutMapping(value = "/deposits", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ClientDtoResponse putDeposit(@Valid @RequestBody DepositDtoRequest request,
                                        @CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) {
        return depositService.putDeposit(request, cookie.getValue());
    }

    @GetMapping(value = "/deposits", produces = MediaType.APPLICATION_JSON_VALUE)
    public ClientDtoResponse getClientDeposit(@CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) {
        return depositService.getClientDeposit(cookie.getValue());
    }

}
