package net.thumbtack.onlineshop.controller;


import net.thumbtack.onlineshop.config.AppProperties;
import net.thumbtack.onlineshop.dto.request.ClientDtoRequest;
import net.thumbtack.onlineshop.dto.request.UpdateClientDtoRequest;
import net.thumbtack.onlineshop.dto.response.ClientDtoResponse;
import net.thumbtack.onlineshop.dto.response.InfoAboutAllClientDtoResponse;
import net.thumbtack.onlineshop.dto.response.UserDtoResponse;
import net.thumbtack.onlineshop.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDtoResponse clientRegistration(@Valid @RequestBody ClientDtoRequest req, HttpServletResponse response) {
        return clientService.clientRegistration(req, response);
    }

    @PutMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ClientDtoResponse updateClient(@Valid @RequestBody UpdateClientDtoRequest request,
                                          @CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) {
        return clientService.updateClient(request, cookie.getValue());
    }

    @GetMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE)
    public InfoAboutAllClientDtoResponse getInfoAboutClients(@CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) {
        return clientService.getInfoAboutClients(cookie.getValue());
    }
}
