package net.thumbtack.onlineshop.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CategoryDtoRequest {

    private String name;
    private Integer parentId;

    public CategoryDtoRequest() {
    }

    public CategoryDtoRequest(String name, Integer parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public CategoryDtoRequest(String name) {
        this.parentId = 0;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getParentId() {
        return parentId;
    }
}
