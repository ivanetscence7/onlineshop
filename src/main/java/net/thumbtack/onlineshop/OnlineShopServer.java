package net.thumbtack.onlineshop;

import net.thumbtack.onlineshop.config.AppProperties;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class OnlineShopServer {
    public static void main(String[] args) {
        SpringApplication.run(OnlineShopServer.class, args);
        AppProperties.initSettings();
    }
}