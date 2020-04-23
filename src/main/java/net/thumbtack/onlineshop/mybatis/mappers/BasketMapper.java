package net.thumbtack.onlineshop.mybatis.mappers;


import net.thumbtack.onlineshop.model.Basket;
import net.thumbtack.onlineshop.model.Client;
import net.thumbtack.onlineshop.model.Product;
import net.thumbtack.onlineshop.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface BasketMapper {

    @Insert("INSERT INTO basket_product(id_basket, id_product_in_basket, name_product_in_basket, price_product_in_basket, count_product_in_basket) " +
            "VALUES (#{client.id}, #{product.id}, #{product.name}, #{product.price}, #{product.count})")
    @Options(useGeneratedKeys = true,keyProperty = "product.id")
    void addProductToBasket_Product(@Param("client") Client client, @Param("product") Product product);

    @Select("SELECT * FROM basket WHERE id_client_ref = #{client.id}")
    @Results({
            @Result(property = "id", column = "id_client_ref"),
            @Result(property = "products", column = "id_client_ref", javaType = List.class,
                    many = @Many(select = "getBasketProducts", fetchType = FetchType.LAZY)
            )
    })
    Basket getAllProductsFromBasket(@Param("client") Client client);

    @Select("SELECT id_product_in_basket, name_product_in_basket, price_product_in_basket, count_product_in_basket " +
            "FROM basket_product WHERE id_basket = #{id_basket}")
    @Results({
            @Result(property = "id", column = "id_product_in_basket"),
            @Result(property = "name", column = "name_product_in_basket"),
            @Result(property = "price", column = "price_product_in_basket"),
            @Result(property = "count", column = "count_product_in_basket"),
    })
    List<Product> getBasketProducts(int id_basket);

    @Select("SELECT * FROM basket_product WHERE id_product_in_basket = #{id_product}")
    @Results({
            @Result(property = "id", column = "id_product_in_basket"),
            @Result(property = "name", column = "name_product_in_basket"),
            @Result(property = "price", column = "price_product_in_basket"),
            @Result(property = "count", column = "count_product_in_basket"),
    })
    Product getProductFromBasket_Product(int id_product);

    @Update("UPDATE basket_product SET count_product_in_basket = #{product.count} WHERE id_product_in_basket = #{product.id}")
    void updateProductInBasket_Product(@Param("product") Product product);

    @Delete("DELETE FROM basket_product WHERE id_product_in_basket = #{productId}")
    void deleteProductFromBasket(int productId);

    @Delete("DELETE FROM basket")
    void cleanBasket();
}

