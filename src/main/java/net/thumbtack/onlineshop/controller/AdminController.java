package net.thumbtack.onlineshop.controller;

import net.thumbtack.onlineshop.config.AppProperties;
import net.thumbtack.onlineshop.dto.request.AdminDtoRequest;
import net.thumbtack.onlineshop.dto.request.UpdateAdminDtoRequest;
import net.thumbtack.onlineshop.dto.response.AdminDtoResponse;
import net.thumbtack.onlineshop.exception.ServiceException;
import net.thumbtack.onlineshop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(value = "/admins", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AdminDtoResponse adminRegistration(@Valid @RequestBody AdminDtoRequest reg, HttpServletResponse response) throws ServiceException {
        return adminService.adminRegistration(reg, response);
    }

    @PutMapping(value = "/admins", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AdminDtoResponse updateAdmin(@Valid @RequestBody UpdateAdminDtoRequest request,
                                        @CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) {
        return adminService.updateAdmin(request, cookie.getValue());
    }

}
