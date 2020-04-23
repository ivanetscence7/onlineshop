package net.thumbtack.onlineshop.dto.request;

import javax.validation.constraints.NotBlank;

public class CategoryDtoRequest {

    @NotBlank(message="{category.name.NotEmpty.message}")
    private String name;
    private Integer parentId;

    public CategoryDtoRequest() {

    }

    public CategoryDtoRequest(String name, Integer parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public Integer getParentId() {
        return parentId;
    }
}
