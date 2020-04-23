package net.thumbtack.onlineshop.mybatis.mappers;

import net.thumbtack.onlineshop.model.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Mapper
public interface AdminMapper {

    @Insert("INSERT INTO admin (id_admin, position) VALUES (#{user.id},#{user.position})")
    @Options(useGeneratedKeys = true)
    void insertAdmin(@Param("user") User user);

    @Update("UPDATE admin SET position = #{position} WHERE id_admin = #{id}")
    void updateAdmin(User admin);
}
