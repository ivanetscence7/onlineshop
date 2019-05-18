package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.dto.response.EmptyResponse;
import net.thumbtack.onlineshop.model.Session;
import net.thumbtack.onlineshop.model.User;

import javax.servlet.http.Cookie;

public interface UserDao {

    User login(String login, String password);

    int logout(String cookie);

    void addSession(Session session);

    User getUserByToken(String token);
}
