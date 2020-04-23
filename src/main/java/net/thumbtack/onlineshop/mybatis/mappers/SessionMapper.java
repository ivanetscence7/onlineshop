package net.thumbtack.onlineshop.mybatis.mappers;

import net.thumbtack.onlineshop.model.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

import static org.apache.ibatis.mapping.FetchType.LAZY;

@Component
@Mapper
public interface SessionMapper {

    @Insert("INSERT INTO  user (firstName, lastName, patronymic, login, password, userType)  VALUES" +
            "(#{firstName}, #{lastName}, #{patronymic}, #{login}, #{password}, #{userType} )")
    @Options(useGeneratedKeys = true)
    void insertUser(User user);

    @Insert("INSERT INTO session(id_user, cookie) VALUES " +
            "( #{user.id}, #{cookie.value} )")
    @Options(useGeneratedKeys = true)
    void insertSession(@Param("user") User user, @Param("cookie") Cookie cookie);

    @Delete("DELETE FROM session WHERE cookie = #{cookie}")
    int logout(String cookie);

    @Select("SELECT * FROM user " +
            "LEFT JOIN admin ON user.id = admin.id_admin " +
            "LEFT JOIN client ON user.id = client.id_client " +
            "WHERE login = #{login}")
    @TypeDiscriminator(
            column = "userType",
            cases = {
                    @Case(value = "ADMIN", type = Admin.class,
                            results = {
                                    @Result(property = "position", column = "position")}),
                    @Case(value = "CLIENT", type = Client.class,
                            results = {
                                    @Result(property = "email", column = "email"),
                                    @Result(property = "address", column = "address"),
                                    @Result(property = "phone", column = "phone"),
                                    @Result(property = "deposit", column = "id_client", javaType = Deposit.class,
                                            one = @One(select = "net.thumbtack.onlineshop.mybatis.mappers.ClientMapper.getDepositById", fetchType = LAZY)),
                                    @Result(property = "basket", column = "id_client", javaType = Basket.class,
                                            one = @One(select = "net.thumbtack.onlineshop.mybatis.mappers.BasketMapper.getAllProductsFromBasket", fetchType = LAZY)),
                            })
            }
    )
    User getUserByLogin(String login);

    @Select("SELECT * FROM user " +
            "LEFT JOIN admin ON user.id = admin.id_admin " +
            "LEFT JOIN client ON user.id = client.id_client " +
            "WHERE user.id = (SELECT id_user FROM session WHERE cookie = #{token} )")
    @TypeDiscriminator(
            column = "userType",
            cases = {
                    @Case(value = "ADMIN", type = Admin.class,
                            results = {
                                    @Result(property = "position", column = "position")}),
                    @Case(value = "CLIENT", type = Client.class,
                            results = {
                                    @Result(property = "email", column = "email"),
                                    @Result(property = "address", column = "address"),
                                    @Result(property = "phone", column = "phone"),
                                    @Result(property = "deposit", column = "id_client", javaType = Deposit.class,
                                            one = @One(select = "net.thumbtack.onlineshop.mybatis.mappers.ClientMapper.getDepositById", fetchType = LAZY)),
                                    @Result(property = "basket", column = "id_client", javaType = Basket.class,
                                            one = @One(select = "net.thumbtack.onlineshop.mybatis.mappers.BasketMapper.getAllProductsFromBasket", fetchType = LAZY)),
                            })
            }
    )
    User getUserByToken(String token);

    @Update("UPDATE user SET firstName = #{user.firstName}, lastName = #{user.lastName}, patronymic = #{user.patronymic}, password = #{user.password} WHERE id = #{user.id}")
    void updateUser(@Param("user") User user);

    @Delete("DELETE FROM session WHERE id_user = #{id}")
    void deleteTokenByUserId(int id);

    @Insert("INSERT INTO session (cookie, id_user) VALUES ( #{token}, #{user.id} )")
    void insertToken(@Param("user") User user, @Param("token") String token);

    @Delete("DELETE FROM user")
    void deleteAllUsers();

    @Insert("INSERT INTO session (id_user,cookie) VALUES (#{id}, #{token})ON DUPLICATE KEY UPDATE cookie = #{token}")
    void insertOnUpdateToken(int id, String token);

}
