package net.thumbtack.onlineshop.mybatis.mappers;

import net.thumbtack.onlineshop.model.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Component
@Mapper
public interface AdminMapper {

    @Insert("INSERT INTO admin (id, position) VALUES " +
            "(#{user.id},#{user.position})")
    @Options(useGeneratedKeys = true)
    int insertToAdmin(@Param("user") User user);

    @Select("SELECT user.id, firstName,lastName, patronymic, position FROM user JOIN admin ON user.id = admin.id WHERE user.id = #{user.id} ")
    Admin getAdminById(@Param("user") User user);

    @Insert("INSERT INTO category (name) VALUES (#{category.name})")
    @Options(useGeneratedKeys = true, keyProperty = "category.id")
    int addCategory(@Param("category") Category category);

    @Insert("INSERT INTO category (name, parentId) VALUES (#{name}, #{id})")
    @Options(useGeneratedKeys = true)
    int addSubCategory(Category category);


        @Insert("INSERT INTO product (name,price,count) values (#{name}, #{price}, #{count})")
//    @Insert({
//            "<script>",
//            "INSERT INTO product",
//
//            "</script>",
//    })
    @Options(useGeneratedKeys = true)
    int addProduct(Product product);

    @Insert("INSERT INTO category_product(id_category, id_product) VALUES (#{categoryId}, #{productId})")
    void setCategories(@Param("productId") int id, @Param("categoryId") Integer c);

    @Delete("DELETE FROM category_product WHERE id_product = #{product.id} ")
    void deleteProductCategories(@Param("product") Product product);

    @Select("SELECT * FROM user " +
            "JOIN client ON user.id = client.id_c WHERE userType= #{type}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "firstName", column = "firstName"),
            @Result(property = "lastName", column = "lastName"),
            @Result(property = "patronymic", column = "patronymic"),
            @Result(property = "email", column = "email"),
            @Result(property = "address", column = "address"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "userType", column = "userType")
    })
    List<Client> getAllClients(UserType type);

    @Select("SELECT * FROM client WHERE id_c = #{id}")
    Client getClientById(int id);

    @Update("UPDATE admin SET position = #{position}")
    int updateAdmin(User admin);

    @Select("SElECT * FROM category WHERE id = #{categoryId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "parent", column = "parentId", javaType = Category.class,
                    one = @One(select = "getCategoryById"))
    })
    Category getCategoryWithSubCategory(@Param("categoryId") int categoryId);

    @Select("SELECT * FROM category WHERE id = #{id}")
    Category getCategoryById(int id);


    @Update("UPDATE category SET name = #{category.name} WHERE id = #{categoryId}")
    int updateCategory(@Param("categoryId") int categoryId, @Param("category") Category category);

    @Update("UPDATE category SET name = #{category.name}, parentId = #{category.id} WHERE id = #{categoryId}")
    int updateSubCategory(@Param("categoryId") int categoryId, @Param("category") Category category);


    @Delete("DELETE FROM category WHERE id = #{categoryId} OR parentId = #{categoryId}")
    void deleteCategoryById(@Param("categoryId") int categoryId);

    @Select("SELECT * FROM category")
    @Results({
//            @Result(property = "id", column = "id"),
//            @Result(property = "name", column = "name"),
            @Result(property = "parent", column = "parentId", javaType = Category.class,
                    one = @One(select = "getCategoryWithSubCategory")
            ),
            @Result(property = "subCategories", column = "id", javaType = List.class,
                    many = @Many(select = "getListCategoryById")
            )
    })
        // @Select("SELECT * FROM category")
    List<Category> getCategories();

    @Select("SELECT * FROM category WHERE parentId = #{id}")
    List<Category> getListCategoryById(int id);

    @Update({
            "<script>",
            "UPDATE category" +
                    "<set>" +
                    "<if test = '#{category.name} != null'>" +
                    "name = #{category.name}," +
                    "</if>"+
                    "<if test = '#{category.id} != null'>" +
                    "parentId = #{category.id}" +
                    "</if>"+
                    "</set>"+
                    "WHERE id = #{categoryId}",
            "</script>"
    })
   Integer updateMyRoyalCategory(@Param("categoryId")Integer id, @Param("category")Category category);


    @Select("SELECT * FROM product WHERE id = #{productId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "price", column = "price"),
            @Result(property = "categories", column = "id", javaType = List.class,
                    many = @Many(select= "getProductCategories", fetchType = FetchType.LAZY)
            )
    })
    Product getProductById(int productId);

    @Select("SELECT * FROM category WHERE id IN(" +
            "SELECT id_category FROM category_product WHERE id_product = #{id})")
//    @Results({
//            @Result
//    })
    List<Category> getProductCategories(int id);


    @Update({
            "<script>",
            "UPDATE product" +
                    "<set>" +
                    "<if test = ' name != null'>" +
                    "name = #{name}," +
                    "</if>"+
                    "<if test = ' price != null'>" +
                    "price = #{price}," +
                    "</if>"+
                    "<if test = ' count != null'>" +
                    "count = #{count}" +
                    "</if>"+
                    "</set>"+
                    "WHERE id = #{id}",
            "</script>"
    })
    Integer updateProduct(Product product);
}

