package net.thumbtack.onlineshop.controller;

import net.thumbtack.onlineshop.config.Config;
import net.thumbtack.onlineshop.dto.request.ProductDtoRequest;
import net.thumbtack.onlineshop.dto.request.CategoryDtoRequest;
import net.thumbtack.onlineshop.dto.request.RegistrationAdminDtoRequest;
import net.thumbtack.onlineshop.dto.request.UpdateAdminDtoRequest;
import net.thumbtack.onlineshop.dto.response.*;
import net.thumbtack.onlineshop.model.Category;
import net.thumbtack.onlineshop.service.AdminService;
import net.thumbtack.onlineshop.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    //3.2 Admin registration
    @PostMapping(value = "/admins", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public InputAdminDtoResponse adminRegistration(@Valid @RequestBody RegistrationAdminDtoRequest reg, HttpServletResponse response) throws ServiceException {
        return adminService.adminRegistration(reg, response);
    }

    //3.7 get info about clients
    @GetMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InfoAboutClientDtoResponse> getInfoAboutClients(@CookieValue(value = Config.COOKIE_NAME, required = false) Cookie cookie) {
        return adminService.getInfoAboutClients(cookie.getValue());
    }

    //3.8 update Admin
    @PutMapping(value = "/admins", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public InputAdminDtoResponse updateAdmin(@RequestBody UpdateAdminDtoRequest request,
                                             @CookieValue(value = Config.COOKIE_NAME, required = false) Cookie cookie) {
        return adminService.updateAdmin(request, cookie.getValue());
    }

    //3.10 add Category
    @PostMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDtoResponse addCategory(@RequestBody CategoryDtoRequest req, @CookieValue(value = Config.COOKIE_NAME, required = false) Cookie cookie) {
        return adminService.addCategory(req, cookie.getValue());
    }

    //3.11 get Category by id
    @GetMapping(value = "categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDtoResponse getCategoryById(@PathVariable("id") int categoryId,
                                               @CookieValue(value = Config.COOKIE_NAME, required = false) Cookie cookie) {
        return adminService.getCategoryById(categoryId, cookie.getValue());
    }

    //3.12 update Category +- !!!!
    @PutMapping(value = "categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDtoResponse updateCategory(@PathVariable("id") int categoryId, @RequestBody CategoryDtoRequest request,
                                              @CookieValue(value = Config.COOKIE_NAME, required = false) Cookie cookie){
        return adminService.updateCategory(categoryId, request,cookie.getValue());
    }

    //3.13 delete Category ++-
    @DeleteMapping(value = "categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public EmptyResponse deleteCategory(@PathVariable("id") int categoryId, @CookieValue(value = Config.COOKIE_NAME, required = false) Cookie cookie){
        return adminService.deleteCategory(categoryId, cookie.getValue());
    }

    //3.14 get list all Categories - !!!!!!!!
    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> getCategories(@CookieValue(value = Config.COOKIE_NAME, required = false) Cookie cookie) {
        return adminService.getCategories(cookie.getValue());
    }

    //3.15 add Product
    @PostMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductDtoResponse addProduct(@RequestBody ProductDtoRequest request,
                                         @CookieValue(value = Config.COOKIE_NAME, required = false) Cookie cookie){
        return adminService.addProduct(request, cookie.getValue());
    }


    @PutMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductDtoResponse updateProduct(@PathVariable("id") int productId, @RequestBody ProductDtoRequest request,
                                            @CookieValue(value = Config.COOKIE_NAME, required = false) Cookie cookie){
        return adminService.updateProduct(productId, cookie.getValue(),request);
    }




}
