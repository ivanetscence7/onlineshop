package net.thumbtack.onlineshop.controller;

import net.thumbtack.onlineshop.config.Config;
import net.thumbtack.onlineshop.dto.request.LoginDtoRequest;
import net.thumbtack.onlineshop.dto.response.EmptyResponse;
import net.thumbtack.onlineshop.dto.response.InputUserResponse;
import net.thumbtack.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //3.4 login
    @PostMapping(value = "/sessions/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public InputUserResponse login(@Valid @RequestBody LoginDtoRequest req, HttpServletResponse response){
        return userService.login(req,response);
    }

    //3.5 logout
    @DeleteMapping(value = "/sessions", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
        public EmptyResponse logout(@CookieValue(value = Config.COOKIE_NAME, required = false)Cookie cookie ) {
        return userService.logout(cookie.getValue());
    }

    //3.6 get info about current user
    @GetMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public InputUserResponse getInfoAboutCurUser(@CookieValue(value = Config.COOKIE_NAME, required = false)Cookie cookie, HttpServletResponse response){
        return userService.getInfoAboutCurUser(cookie.getValue(), response);
    }
}
