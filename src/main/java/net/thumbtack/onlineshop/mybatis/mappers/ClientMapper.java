package net.thumbtack.onlineshop.mybatis.mappers;

import net.thumbtack.onlineshop.model.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.apache.ibatis.mapping.FetchType.LAZY;

@Component
@Mapper
public interface ClientMapper {

    @Insert("INSERT INTO client (id_c, email, address, phone) VALUES " +
            "(#{user.id}, #{user.email}, #{user.address}, #{user.phone})")
    @Options(useGeneratedKeys = true)
    int insertToClient(@Param("user") User user);


    @Select("SELECT user.id, firstName, lastName, patronymic, email, address, phone, amount FROM user " +
            "JOIN client ON (user.id = client.id_c) JOIN deposit ON (user.id = deposit.id_c) WHERE user.id = #{user.id}")
    Client getClientById(@Param("user") User user);


    @Insert("INSERT INTO deposit (clientId, amount) VALUES " +
            "(#{user.id}, #{user.deposit.deposit})")
    @Options(useGeneratedKeys = true)
    int insertToDeposit(@Param("user") Client user);


    @Select("SELECT * FROM deposit WHERE clientId = #{id}")
    Deposit getDepositById(int id);

    @Insert("INSERT INTO basket(clientId, productId) VALUES ( #{client.id}, #{product.id} )")
    @Options(useGeneratedKeys = true, keyProperty = "client.id", keyColumn = "id_c")
    int addProductToBasket(@Param("client") Client client, @Param("product") Product product);


    @Select("SELECT * FROM basket WHERE clientId = #{client.id}")
    @Results({
            @Result(property = "id", column = "id_b"),
            @Result(property = "products", column = "id_b", javaType = List.class,
            many = @Many(select = "net.thumbtack.onlineshop.mybatis.mappers.ClientMapper.getProducts", fetchType = LAZY))
    })
    Basket getAllProductsInBasket(@Param("client") User client);

    @Select("SELECT * FROM product WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "price", column = "price"),
            @Result(property = "count", column = "count")
    })
    List<Product> getProducts(int id);

    @Update("UPDATE client SET email = #{email}, address = #{address}, phone = #{phone}")
    int updateClient(User client);
}
