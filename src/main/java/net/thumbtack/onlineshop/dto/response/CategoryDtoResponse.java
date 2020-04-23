package net.thumbtack.onlineshop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDtoResponse implements Comparable<CategoryDtoResponse> {

    @JsonUnwrapped
    private Integer id;
    @JsonUnwrapped
    private String name;
    @JsonUnwrapped
    private Integer parentId;
    @JsonUnwrapped
    private String parentName;
    private List<ProductDtoResponse> products;

    public CategoryDtoResponse() {

    }

    public CategoryDtoResponse(Integer id, String name, Integer parentId, String parentName) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.parentName = parentName;
    }

    public CategoryDtoResponse(Integer id, String name, List<ProductDtoResponse> products) {
        this(id,name);
        this.products = products;
    }

    public CategoryDtoResponse(Integer id, String name, Integer parentId, String parentName, List<ProductDtoResponse> products) {
        this(id,name,parentId,parentName);
        this.products = products;
    }

    public CategoryDtoResponse(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public List<ProductDtoResponse> getProducts() {
        return products;
    }

    @Override
    public int compareTo(CategoryDtoResponse o) {
        return name.compareTo(o.getName());
    }
}
