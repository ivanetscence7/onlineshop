package net.thumbtack.onlineshop.mybatis.mappers;

import net.thumbtack.onlineshop.model.Category;
import net.thumbtack.onlineshop.model.Product;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Mapper
public interface ProductMapper {


    @Insert("INSERT INTO product (name,price,count) values (#{product.name}, #{product.price}, #{product.count})")
    @Options(useGeneratedKeys = true, keyProperty = "product.id")
    void addProduct(@Param("product") Product product);

    @Select("SELECT * FROM product WHERE id = #{productId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "price", column = "price"),
            @Result(property = "count", column = "count"),
            @Result(property = "categories", column = "id", javaType = List.class,
                    many = @Many(select = "getProductCategories", fetchType = FetchType.LAZY)
            )
    })
    Product getProductById(int productId);


    @Update({
            "<script>",
            "UPDATE product" +
                    "<set>" +
                    "<if test = ' name != null'>" +
                    "name = #{name}," +
                    "</if>" +
                    "<if test = ' price != null'>" +
                    "price = #{price}," +
                    "</if>" +
                    "<if test = ' count != null'>" +
                    "count = #{count}" +
                    "</if>" +
                    "</set>" +
                    "WHERE id = #{id}",
            "</script>"
    })
    void updateProduct(Product product);

    @Select("SELECT id, name, price, count FROM product WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "categories", column = "id", javaType = List.class,
                    many = @Many(select = "getByProduct", fetchType = FetchType.LAZY))
    })
    Product getAllProducts(int id);

    @Select({"<script>",
            "SELECT product.id, product.name, product.price, product.count, category.name FROM product LEFT JOIN category_product on product.id = category_product.id_product",
            "<choose>",
            "<when test='category != null'>",
            "LEFT JOIN category on category_product.id_category = category.id WHERE category.id IN (" +
                    "<foreach item='item' collection='category' separator=','>",
            "(#{item})",
            "</foreach>",
            " )ORDER BY product.name;",
            "</when>",
            "<when test='order.equals(\"product\")'>",
            "LEFT JOIN category on category_product.id_category = category.id ORDER BY product.name",
            "</when>",
            "<when test='order.equals(\"category\")'>",
            "LEFT JOIN category on category_product.id_category = category.id ORDER BY  product.name, category.name",
            "</when>",
            "<otherwise>",
            "LEFT JOIN category on category_product.id_category = category.id",
            "</otherwise>",
            "</choose>",
            "</script>"})
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "price", column = "price"),
            @Result(property = "count", column = "count"),
            @Result(property = "categories", column = "id", javaType = List.class,
                    many = @Many(select = "getProductCategories", fetchType = FetchType.LAZY)
            )
    })
    List<Product> getAllProductsByParams(@Param("category") List<Integer> category, @Param("order") String order);

    @Select("SELECT * FROM category WHERE id IN(" +
            "SELECT id_category FROM category_product WHERE id_product = #{id})")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "products", column = "id", javaType = List.class,
                    many = @Many(select = "getProductWithCategoryOrder")
            )
    })
    List<Category> getProductCategories(int id);

    @Select("SELECT * FROM product WHERE id IN(" +
            "SELECT id_product FROM category_product WHERE id_category = #{id})")
    List<Product> getProductWithCategoryOrder(int id);

    @Insert({"<script>",
            "INSERT INTO category_product (id_category, id_product) VALUES",
            "<foreach item='item' collection='list' separator=','>",
            "(#{item.id}, #{productId})",
            "</foreach>",
            "</script>"})
    void setCategories(@Param("productId") int id, @Param("list") List<Category> categories);

    @Update({"<script>",
            "UPDATE category_product",
            "<set>",
            "<if test = ' id_category != null'>" +
                    "<foreach item='item' collection='list' separator=','>" +
                    "id_category = #{item.id}" +
                    "</foreach>" +
                    "</if>" +
                    "</set>" +
                    "WHERE id = #{id}" +
                    "</script>"})
    void setUpdateCategories(@Param("id") int id, @Param("list") List<Category> categories);


    @Delete("DELETE FROM category_product WHERE id_product = #{product.id} ")
    void deleteCurrentProductCategories(@Param("product") Product product);

    @Delete("DELETE FROM product")
    void deleteAllProducts();

    @Delete(value = "DELETE FROM product WHERE id = #{productId}")
    void deleteProductById(int productId);

    @Update("UPDATE product SET count = #{product.count}, version = version + 1 " +
            "WHERE name = #{product.name} AND version = #{product.version}")
    int updateProductCount(@Param("product") Product product);

    @Select("SELECT * FROM product WHERE name = #{name}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "price", column = "price"),
            @Result(property = "count", column = "count"),
            @Result(property = "categories", column = "id", javaType = List.class,
                    many = @Many(select = "getProductCategories", fetchType = FetchType.LAZY)
            )
    })
    Product getProductByName(String name);


    @Select({"<script>",
            "SELECT * FROM product",
            "<choose>",
            "<when test='product != null'>",
            "WHERE id IN (" +
                    "<foreach item='item' collection='product' separator=','>",
            "(#{item})",
            "</foreach>",
            ");",
            "</when>",
            "</choose>",
            "</script>"})
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "price", column = "price"),
            @Result(property = "count", column = "count"),
            @Result(property = "version", column = "version"),
            @Result(property = "categories", column = "id", javaType = List.class,
                    many = @Many(select = "getProductCategories", fetchType = FetchType.LAZY)
            )
    })
    List<Product> getProductsByListId(@Param("product") List<Integer> product);

}
