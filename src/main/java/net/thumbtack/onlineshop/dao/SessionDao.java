package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.model.Product;
import net.thumbtack.onlineshop.model.Session;
import net.thumbtack.onlineshop.model.User;

public interface SessionDao {

    int logout(String cookie);

    User getUserByToken(String token);

    User getUserByLogin(String login);

    void insertToken(String token, User user);


}
