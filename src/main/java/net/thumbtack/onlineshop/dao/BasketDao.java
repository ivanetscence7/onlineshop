package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.model.Basket;
import net.thumbtack.onlineshop.model.Client;
import net.thumbtack.onlineshop.model.Product;

public interface BasketDao {
    Product addProductToBasket(Client client, Product product);

    Basket getAllProductsFromBasket(Client client);

    void deleteProductFromBasket(int productId);

    void updateProductInBasket(Product product);
}
