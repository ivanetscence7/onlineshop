package net.thumbtack.onlineshop.mybatis.mappers;


import net.thumbtack.onlineshop.model.Product;
import net.thumbtack.onlineshop.model.Purchase;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Mapper
public interface PurchaseMapper {

    @Insert("INSERT INTO purchase(id_client_ref, date_purchase, purchase_amount) VALUES (#{id_client},#{purchase.date},#{purchase.amount})")
    @Options(useGeneratedKeys = true, keyProperty = "purchase.id")
    void addPurchase(@Param("id_client") int id_client, @Param("purchase") Purchase purchase);

    @Insert("INSERT INTO purchase_product (id_purchase, name_product_purchase, price_product_purchase, count_product_purchase) " +
            "VALUES ( #{purchase.id}, #{product.name}, #{product.price}, #{product.count} ) ")
    @Options(useGeneratedKeys = true, keyProperty = "purchase.id")
    void addPurchaseProduct(@Param("purchase") Purchase purchase, @Param("product") Product product);

    @Insert({"<script>",
            "INSERT INTO purchase_product (id_purchase, name_product_purchase, price_product_purchase, count_product_purchase)VALUES",
            "<foreach item='item' collection='list' separator=','>",
            "(#{purchase.id}, #{item.name}, #{item.price}, #{item.count} )",
            "</foreach>",
            "</script>"})
    void addPurchaseProductFromBasket(@Param("purchase") Purchase purchase, @Param("list") List<Product> product);

    @Select({"<script>",
            "SELECT * FROM purchase WHERE DATE(date_purchase)",
                "<if test = 'criterion != null'>",
                       "<if test = 'limit != null and offset != null'>",
                        "<choose>",
                            "<when test = 'criterion.equals(\"UP\")'>",
                                "BETWEEN #{startDate} AND #{endDate} ORDER BY purchase_amount LIMIT #{limit} OFFSET #{limit}",
                            "</when>",
                            "<when test = 'criterion.equals(\"DOWN\")'>",
                                "BETWEEN #{startDate} AND #{endDate} ORDER BY purchase_amount DESC LIMIT #{limit} OFFSET #{limit}",
                            "</when>",
                        "</choose>",
                    "</if>",
                "</if>",
                "<if test = 'limit == null or offset == null'>",
                        "<choose>",
                         "<when test = 'criterion.equals(\"UP\")'>",
                                "BETWEEN #{startDate} AND #{endDate} ORDER BY purchase_amount",
                        "</when>",
                        "<when test = 'criterion.equals(\"DOWN\")'>",
                                 "BETWEEN #{startDate} AND #{endDate} ORDER BY purchase_amount DESC",
                         "</when>",
                        "</choose>",
                     "</if>",
                 "<if test = 'criterion==null and limit != null and offset != null'>",
                       "BETWEEN #{startDate} AND #{endDate} LIMIT #{limit} OFFSET #{limit}",
                  "</if>",
            "</script>"
    })
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date_purchase"),
            @Result(property = "amount", column = "purchase_amount"),
            @Result(property = "products", column = "id", javaType = List.class,
                    many = @Many(select = "getListPurchaseProductById", fetchType = FetchType.LAZY)
            )
    })
    List<Purchase> getProfitByDateRange(@Param("startDate") Date startDare, @Param("endDate") Date endDate,
                                        @Param("limit") Integer limit, @Param("offset") Integer offset, @Param("criterion") String criterion);


    @Select("SELECT * FROM purchase WHERE id_client_ref = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date_purchase"),
            @Result(property = "amount", column = "purchase_amount"),
            @Result(property = "products", column = "id_product", javaType = List.class,
                    many = @Many(select = "getListPurchaseProductById", fetchType = FetchType.LAZY)
            )
    })
    List<Purchase> getPurchaseByClientId(@Param("id") Integer id);

    @Select("SELECT * FROM purchase_product WHERE id = #{id}")
    @Results({
            @Result(property = "name", column = "name_product_purchase"),
            @Result(property = "price", column = "price_product_purchase"),
            @Result(property = "count", column = "count_product_purchase"),
    })
    List<Product> getListPurchaseProductById(int id);

    @Delete("DELETE FROM purchase")
    void deleteAllPurchases();

    //    @Select("SELECT * FROM purchase WHERE DATE(date_purchase) BETWEEN #{startDate} AND #{endDate}")
//    @Results({
//            @Result(property = "id", column = "id"),
//            @Result(property = "date", column = "date_purchase"),
//            @Result(property = "amount", column = "purchase_amount"),
//            @Result(property = "products", column = "id", javaType = List.class,
//                    many = @Many(select = "getListPurchaseProductById", fetchType = FetchType.LAZY)
//            )
//    })
//    List<Purchase> getProfitByDateRange(@Param("startDate") Date startDare, @Param("endDate") Date endDate,
//                                        @Param("offset") Integer offset, @Param("limit") Integer limit, @Param("criterion") String criterion);

}
