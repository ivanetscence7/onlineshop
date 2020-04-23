package net.thumbtack.onlineshop.dto.response;

import java.util.List;

public class AllCategoryDtoResponse extends StatementDtoResponse {

    private List<CategoryDtoResponse> categories;

    public AllCategoryDtoResponse() {
    }

    public AllCategoryDtoResponse(List<CategoryDtoResponse> categories) {
        this.categories = categories;
    }

    public List<CategoryDtoResponse> getCategories() {
        return categories;
    }
}
