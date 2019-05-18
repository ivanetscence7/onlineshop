package net.thumbtack.onlineshop.daoimpl;

import net.thumbtack.onlineshop.dao.UserDao;
import net.thumbtack.onlineshop.dto.response.EmptyResponse;
import net.thumbtack.onlineshop.model.Session;
import net.thumbtack.onlineshop.model.User;
import net.thumbtack.onlineshop.mybatis.mappers.ClientMapper;
import net.thumbtack.onlineshop.mybatis.mappers.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
public class UserDaoImpl implements UserDao {

    private UserMapper userMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    public UserDaoImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public User login(String login, String password) {
       return userMapper.login(login,password);
    }

    @Override
    public int logout(String cookie) {
        return userMapper.logout(cookie);
    }

    @Override
    public void addSession(Session session) {
        userMapper.addSession(session);
    }

    @Override
    public User getUserByToken(String token) {
        return userMapper.getUserByToken(token);
    }


}
