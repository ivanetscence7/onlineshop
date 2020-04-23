package net.thumbtack.onlineshop.mybatis.mappers;

import net.thumbtack.onlineshop.model.Category;
import net.thumbtack.onlineshop.model.Product;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CategoryMapper {


    @Insert("INSERT INTO category (name, id_parent) VALUE (#{category.name}, #{category.parent.id})")
    @Options(useGeneratedKeys = true,keyProperty = "category.id")
    void addCategory(@Param("category") Category category);

    @Update({
            "<script>",
            "UPDATE category" +
                    "<set>" +
                    "<if test = 'category.name == null'>" +
                    "name = #{category.name}," +
                    "</if>" +
                    "<if test = 'category.name != null'>" +
                    "name = #{category.name}," +
                    "</if>" +
                    "<if test = 'category.parent == null'>" +
                    "id_parent = null" +
                    "</if>" +
                    "<if test = 'category.parent != null'>" +
                    "name = #{category.name}," +
                    "</if>" +
                    "<if test = 'category.parent != null'>" + //+
                    "id_parent = #{category.parent.id}" +
                    "</if>" +
                    "</set>" +
                    "WHERE id = #{categoryId}",
            "</script>"
    })
    void updateCategory(@Param("categoryId") Integer id, @Param("category") Category category);

    @Select("SELECT * FROM category WHERE id = #{id}")
    Category getCategoryById(Integer id);

    @Delete("DELETE FROM category WHERE id_parent = #{categoryId} OR id = #{categoryId}")
    void deleteCategoryById(@Param("categoryId") int categoryId);

    @Select("SELECT * FROM category ORDER BY name")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "parent", column = "id_parent", javaType = Category.class,
                    one = @One(select = "getCategoryWithSubCategory", fetchType = FetchType.LAZY))
    })
    List<Category> getCategories();

    @Select("SElECT * FROM category WHERE id = #{categoryId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "parent", column = "id_parent", javaType = Category.class,
                    one = @One(select = "getCategoryById", fetchType = FetchType.LAZY))
    })
    Category getCategoryWithSubCategory(@Param("categoryId") Integer categoryId);

    @Select("SELECT * FROM category WHERE id_parent = #{id}")
    List<Category> getListCategoryById(int id);

    @Delete("DELETE FROM category")
    void deleteAllCategories();

    @Select({"<script>",
                "SELECT * FROM category",
            "<choose>",
                "<when test='category != null'>",
                    "WHERE id IN (" +
                    "<foreach item='item' collection='category' separator=','>",
                    "(#{item})",
                    "</foreach>",
                        ");",
                 "</when>",
            "</choose>",
            "</script>"
    })
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "parent", column = "id_parent", javaType = Category.class,
                    one = @One(select = "getCategoryWithSubCategory", fetchType = FetchType.LAZY)),
            @Result(property = "products", column = "id", javaType = List.class,
                    many = @Many(select = "getCategoryProducts", fetchType =  FetchType.LAZY))
    })
    List<Category> getCategoriesByListId(@Param("category") List<Integer> category);

    @Select("SELECT * FROM product WHERE id IN(" +
            "SELECT id_product FROM category_product WHERE id_category = #{id})")
    List<Product> getCategoryProducts(int id);

}
