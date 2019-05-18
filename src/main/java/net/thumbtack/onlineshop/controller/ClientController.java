package net.thumbtack.onlineshop.controller;


import net.thumbtack.onlineshop.config.Config;
import net.thumbtack.onlineshop.dto.request.AddProductToBasketDtoRequest;
import net.thumbtack.onlineshop.dto.request.RegistrationClientDtoRequest;
import net.thumbtack.onlineshop.dto.request.UpdateClientDtoRequest;
import net.thumbtack.onlineshop.dto.response.AddProductToBasketDtoResponse;
import net.thumbtack.onlineshop.dto.response.InputClientDtoResponse;
import net.thumbtack.onlineshop.dto.response.InputUserResponse;
import net.thumbtack.onlineshop.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    //3.3 Client registration
    @PostMapping(value= "/clients/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public InputUserResponse clientRegistration(@Valid @RequestBody RegistrationClientDtoRequest req, HttpServletResponse response){
        return clientService.clientRegistration(req,response);
    }

    //3.9 update client
    @PutMapping(value = "/clients",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public InputClientDtoResponse updateClient(@RequestBody UpdateClientDtoRequest request,
                                               @CookieValue(value = Config.COOKIE_NAME, required = false)Cookie cookie){
        return clientService.updateClient(request, cookie.getValue());
    }

    //3.23
    ////////&&&&&&&&&&&&&&!!!!!!!!!!!!!!
    @PostMapping(value = "/baskets", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<AddProductToBasketDtoResponse> addProductToBasket(@RequestBody AddProductToBasketDtoRequest request,
                                                                  @CookieValue(value = Config.COOKIE_NAME, required = false)Cookie cookie){
        return clientService.addProductToBasket(request, cookie.getValue());
    }



}
