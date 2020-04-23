package net.thumbtack.onlineshop.controller;

import net.thumbtack.onlineshop.config.AppProperties;
import net.thumbtack.onlineshop.dto.request.LoginDtoRequest;
import net.thumbtack.onlineshop.dto.response.EmptyResponse;
import net.thumbtack.onlineshop.dto.response.UserDtoResponse;
import net.thumbtack.onlineshop.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class SessionController {

    private SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping(value = "/sessions", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDtoResponse login(@Valid @RequestBody LoginDtoRequest req, HttpServletResponse response){
        return sessionService.login(req,response);
    }

    @DeleteMapping(value = "/sessions", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmptyResponse logout(@CookieValue(value = AppProperties.COOKIE_NAME, required = false)Cookie cookie, HttpServletResponse response ) {
        return sessionService.logout(cookie.getValue(),response);
    }


}
