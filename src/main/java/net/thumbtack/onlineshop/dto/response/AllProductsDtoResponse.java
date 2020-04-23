package net.thumbtack.onlineshop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllProductsDtoResponse extends StatementDtoResponse {

    private List<ProductWithCategoryNameDtoResponse> resultsByOrderProductOrCategoryList;
    private Set<ProductWithAboveCategoryNameDtoResponse> resultsByOrderCategory;

    public AllProductsDtoResponse() {
    }

    public AllProductsDtoResponse(List<ProductWithCategoryNameDtoResponse> resultsByOrderProductOrCategoryList, Set<ProductWithAboveCategoryNameDtoResponse> resultsByOrderCategory) {
        this.resultsByOrderProductOrCategoryList = resultsByOrderProductOrCategoryList;
        this.resultsByOrderCategory = resultsByOrderCategory;
    }

    public AllProductsDtoResponse(List<ProductWithCategoryNameDtoResponse> resultsByOrderProductOrCategoryList) {
        this.resultsByOrderProductOrCategoryList = resultsByOrderProductOrCategoryList;
    }

    public AllProductsDtoResponse(Set<ProductWithAboveCategoryNameDtoResponse> resultsByOrderCategory) {
        this.resultsByOrderCategory = resultsByOrderCategory;
    }

    public List<ProductWithCategoryNameDtoResponse> getResultsByOrderProductOrCategoryList() {
        return resultsByOrderProductOrCategoryList;
    }

    public void setResultsByOrderProductOrCategoryList(List<ProductWithCategoryNameDtoResponse> resultsByOrderProductOrCategoryList) {
        this.resultsByOrderProductOrCategoryList = resultsByOrderProductOrCategoryList;
    }

    public Set<ProductWithAboveCategoryNameDtoResponse> getResultsByOrderCategory() {
        return resultsByOrderCategory;
    }

    public void setResultsByOrderCategory(Set<ProductWithAboveCategoryNameDtoResponse> resultsByOrderCategory) {
        this.resultsByOrderCategory = resultsByOrderCategory;
    }
}
