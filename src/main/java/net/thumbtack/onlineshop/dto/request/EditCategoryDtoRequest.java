package net.thumbtack.onlineshop.dto.request;

public class EditCategoryDtoRequest {

    private String name;
    private Integer parentId;

    public EditCategoryDtoRequest(String name, Integer parentId) {
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
