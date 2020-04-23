package net.thumbtack.onlineshop.dto.request;

import javax.validation.constraints.NotBlank;

public class UpdateCategoryDtoRequest {

    private String name;
    private Integer parentId;

    public UpdateCategoryDtoRequest() {
    }

    public UpdateCategoryDtoRequest(String name, Integer parentId) {
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
