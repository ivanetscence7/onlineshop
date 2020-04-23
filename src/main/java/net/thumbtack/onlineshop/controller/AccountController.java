package net.thumbtack.onlineshop.controller;

import net.thumbtack.onlineshop.config.AppProperties;
import net.thumbtack.onlineshop.dto.response.UserDtoResponse;
import net.thumbtack.onlineshop.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;

@RestController
@RequestMapping("/api")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDtoResponse getInfoAboutCurUser(@CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie ){
        return accountService.getInfoAboutCurUser(cookie.getValue());
    }

}
