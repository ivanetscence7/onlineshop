package net.thumbtack.onlineshop.controller;

import net.thumbtack.onlineshop.dto.response.EmptyResponse;
import net.thumbtack.onlineshop.service.DebugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api")
public class DebugController {

    private DebugService service;

    @Autowired
    public DebugController(DebugService service) {
        this.service = service;
    }

    @PostMapping(value = "/debug/clear", produces = APPLICATION_JSON_VALUE)
    public EmptyResponse clearDataBase() {
        return service.clearDataBase();
    }
}
