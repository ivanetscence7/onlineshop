package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.model.Basket;
import net.thumbtack.onlineshop.model.Client;
import net.thumbtack.onlineshop.model.Product;
import net.thumbtack.onlineshop.model.User;

import javax.servlet.http.Cookie;
import java.util.List;

public interface ClientDao {

    User clientRegistration(Client user, Cookie cookie);

    Client getClient(User user);

    Product addProductToBasket(Client client, Product product);

    User getClientByToken(String token);

    Basket getAllProductsInBasket(User client);

    User getUserByToken(String token);

    User updateClient(User client);
}
