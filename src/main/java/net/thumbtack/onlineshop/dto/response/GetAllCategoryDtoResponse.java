package net.thumbtack.onlineshop.dto.response;

import net.thumbtack.onlineshop.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GetAllCategoryDtoResponse {

    List<Category> list;

    public GetAllCategoryDtoResponse(List<Category> list) {
        this.list = list;
    }

    public List<Category> getList() {
        return list;
    }



}
