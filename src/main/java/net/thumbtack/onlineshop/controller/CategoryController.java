package net.thumbtack.onlineshop.controller;

import net.thumbtack.onlineshop.config.AppProperties;
import net.thumbtack.onlineshop.dto.request.CategoryDtoRequest;
import net.thumbtack.onlineshop.dto.request.UpdateCategoryDtoRequest;
import net.thumbtack.onlineshop.dto.response.AllCategoryDtoResponse;
import net.thumbtack.onlineshop.dto.response.CategoryDtoResponse;
import net.thumbtack.onlineshop.dto.response.EmptyResponse;
import net.thumbtack.onlineshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CategoryController {


    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDtoResponse addCategory(@Valid @RequestBody CategoryDtoRequest req, @CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie){
        return categoryService.addCategory(req, cookie.getValue());
    }

    @GetMapping(value = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDtoResponse getCategoryById(@PathVariable("id") int categoryId,
                                               @CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) {
        return categoryService.getCategoryById(categoryId, cookie.getValue());
    }

    @PutMapping(value = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDtoResponse updateCategory(@PathVariable("id") int categoryId, @Valid @RequestBody UpdateCategoryDtoRequest request,
                                              @CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) {
        return categoryService.updateCategory(categoryId, request, cookie.getValue());
    }

    @DeleteMapping(value = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmptyResponse deleteCategory(@PathVariable("id") int categoryId, @CookieValue(value = AppProperties.COOKIE_NAME) Cookie cookie) {
        return categoryService.deleteCategory(categoryId, cookie.getValue());
    }

    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public AllCategoryDtoResponse getCategories(@CookieValue(value = AppProperties.COOKIE_NAME, required = false) Cookie cookie) {
        return categoryService.getCategories(cookie.getValue());
    }
}
