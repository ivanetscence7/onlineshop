package net.thumbtack.onlineshop.mybatis.mappers;

import net.thumbtack.onlineshop.model.Deposit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface DepositMapper {


    @Update("UPDATE deposit SET amount = #{deposit.amount}, version = version + 1 WHERE id_client_ref = #{id} AND version = #{deposit.version}")
    int putDeposit(@Param("id") int id, @Param("deposit") Deposit deposit);

    @Select("SELECT * FROM deposit WHERE id_client_ref = #{id}")
    Deposit getDepositById(int id);
}
