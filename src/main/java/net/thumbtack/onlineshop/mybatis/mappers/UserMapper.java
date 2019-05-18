package net.thumbtack.onlineshop.mybatis.mappers;

import net.thumbtack.onlineshop.dto.response.EmptyResponse;
import net.thumbtack.onlineshop.model.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

import java.util.List;

import static org.apache.ibatis.mapping.FetchType.LAZY;

@Component
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO  user (firstName, lastName, patronymic, login, password, userType)  VALUES" +
            "(#{firstName}, #{lastName}, #{patronymic}, #{login}, #{password}, #{userType} )")
    @Options(useGeneratedKeys = true)
    int insertUser(User user);

    @Insert("INSERT INTO session(userId, cookie) VALUES " +
            "( #{user.id}, #{cookie.value} )")
    @Options(useGeneratedKeys = true)
    int insertToSession(@Param("user") User user, @Param("cookie") Cookie cookie);

    @Select("SELECT * FROM user WHERE login = #{login} AND password = #{password}")
    User login(@Param("login") String login, @Param("password") String password);

    @Delete("DELETE FROM session WHERE cookie = #{cookie}")
    int logout(String cookie);

    @Update("UPDATE session SET cookie = #{cookie} WHERE userId = session.userId ")
    @Options(useGeneratedKeys = true)
    void addSession(Session session);

    @Select("SELECT * FROM user WHERE login = #{login}")
    User getUserByLogin(@Param("login") String login);

    @Select("SELECT * FROM user " +
            "LEFT JOIN admin ON user.id = admin.id " +
            "LEFT JOIN client ON user.id = client.id_c " +
            "WHERE user.id = (SELECT userId FROM session WHERE cookie = #{token} )")
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
                                    @Result(property = "deposit", column = "id_c", javaType = Deposit.class,
                                            one = @One(select = "net.thumbtack.onlineshop.mybatis.mappers.ClientMapper.getDepositById", fetchType = LAZY)),
                            })
            }
    )
    User getUserByToken(String token);

    @Update("UPDATE user SET firstName = #{firstName}, lastName = #{lastName}, patronymic = #{patronymic}, password = #{password}")
    int updateUser(User user);
}
