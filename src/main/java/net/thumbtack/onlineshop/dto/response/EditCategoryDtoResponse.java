package net.thumbtack.onlineshop.dto.response;

public class EditCategoryDtoResponse {

    private Integer id;
    private String name;
    private Integer parentId;
    private String parentName;

    public EditCategoryDtoResponse(Integer id, String name, Integer parentId, String parentName) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.parentName = parentName;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public String getParentName() {
        return parentName;
    }

}
