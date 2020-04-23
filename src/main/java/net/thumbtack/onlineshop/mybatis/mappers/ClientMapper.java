package net.thumbtack.onlineshop.mybatis.mappers;

import net.thumbtack.onlineshop.model.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.apache.ibatis.mapping.FetchType.LAZY;

@Component
@Mapper
public interface ClientMapper {

    @Select("SELECT * FROM client LEFT JOIN user ON client.id_client = user.id WHERE id_client = #{id}")
    @Results({
            @Result(property = "id", column = "id_client"),
            @Result(property = "userType", column = "userType"),
            @Result(property = "purchases", column = "id_client", javaType = List.class,
                    many = @Many(select = "net.thumbtack.onlineshop.mybatis.mappers.PurchaseMapper.getPurchaseByClientId", fetchType = LAZY)),
            @Result(property = "deposit", column = "id_client", javaType = Deposit.class,
                    one = @One(select = "net.thumbtack.onlineshop.mybatis.mappers.ClientMapper.getDepositById", fetchType = LAZY)),
            @Result(property = "basket", column = "id_client", javaType = Basket.class,
                    one = @One(select = "net.thumbtack.onlineshop.mybatis.mappers.BasketMapper.getAllProductsFromBasket", fetchType = LAZY)),
    })
    Client getClientById(@Param("id") Integer id);

    @Insert("INSERT INTO client (id_client, email, address, phone) VALUES " +
            "(#{user.id}, #{user.email}, #{user.address}, #{user.phone})")
    @Options(useGeneratedKeys = true)
    void insertClient(@Param("user") User user);

    @Select("SELECT * FROM deposit WHERE id_client_ref = #{id}")
    Deposit getDepositById(int id);

    @Insert("INSERT INTO deposit (id_client_ref, amount) VALUES " +
            "(#{user.id}, #{user.deposit.amount})")
    @Options(useGeneratedKeys = true)
    void insertDeposit(@Param("user") Client user);

    @Update("UPDATE client SET email = #{email}, address = #{address}, phone = #{phone} WHERE id_client = #{id}")
    void updateClient(User client);

    @Select("SELECT * FROM user " +
            "JOIN client ON user.id = client.id_client WHERE userType = #{type}")
    @Results({
            @Result(property = "id", column = "id_client"),
            @Result(property = "firstName", column = "firstName"),
            @Result(property = "lastName", column = "lastName"),
            @Result(property = "patronymic", column = "patronymic"),
            @Result(property = "email", column = "email"),
            @Result(property = "address", column = "address"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "userType", column = "userType")
    })
    List<Client> getAllClients(UserType type);

    @Insert("INSERT INTO basket(id_client_ref) VALUES (#{client.id})")
    void insertBasket(@Param("client") Client client);
}
