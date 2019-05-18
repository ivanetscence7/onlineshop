package net.thumbtack.onlineshop;

import net.thumbtack.onlineshop.model.Admin;
import net.thumbtack.onlineshop.model.User;
import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;


@MappedTypes(User.class)
@MapperScan("net.thumbtack.onlineshop.mybatis.mappers")
@SpringBootApplication
public class OnlineShopServer {
	public static void main(String[] args) {
		SpringApplication.run(OnlineShopServer.class, args);
	}
}