package net.thumbtack.onlineshop.daoimpl;

import net.thumbtack.onlineshop.dao.ClientDao;
import net.thumbtack.onlineshop.model.Basket;
import net.thumbtack.onlineshop.model.Client;
import net.thumbtack.onlineshop.model.Product;
import net.thumbtack.onlineshop.model.User;
import net.thumbtack.onlineshop.mybatis.mappers.ClientMapper;
import net.thumbtack.onlineshop.mybatis.mappers.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.List;

@Component
public class ClientDaoImpl implements ClientDao {

    private ClientMapper clientMapper;
    private UserMapper userMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDaoImpl.class);

    @Autowired
    public ClientDaoImpl(ClientMapper clientMapper, UserMapper userMapper) {
        this.clientMapper = clientMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Client clientRegistration(Client user, Cookie cookie) {
        LOGGER.debug("DAO registration  Client {}", user);
        try {
            userMapper.insertUser(user);
            clientMapper.insertToClient(user);
            userMapper.insertToSession(user, cookie);
            clientMapper.insertToDeposit(user);
;        }catch (RuntimeException ex){
            LOGGER.info("DAO can't insert Client {}, {}", user, ex);
            throw ex;
        }
        return user;
    }

    @Override
    public Client getClient(User user) {
        return clientMapper.getClientById(user);
    }

    @Override
    public Product addProductToBasket(Client client, Product product) {
        clientMapper.addProductToBasket(client,product);
        return product;
    }

    @Override
    public User getClientByToken(String token) {
        return userMapper.getUserByToken(token);
    }

    @Override
    public Basket getAllProductsInBasket(User client) {
        Basket basket = clientMapper.getAllProductsInBasket(client);
        return basket;
    }

    @Override
    public User getUserByToken(String token) {
        return userMapper.getUserByToken(token);
    }

    @Override
    public User updateClient(User client) {
        userMapper.updateUser(client);
        clientMapper.updateClient(client);
        return client;
    }
}
