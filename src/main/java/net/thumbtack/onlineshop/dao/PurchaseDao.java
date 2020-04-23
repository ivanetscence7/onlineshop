package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.model.Client;
import net.thumbtack.onlineshop.model.Product;
import net.thumbtack.onlineshop.model.Purchase;

import java.util.Date;
import java.util.List;

public interface PurchaseDao {
    Product purchaseProduct(Client client, Purchase purchase, Product currentProduct, Product onStorageProduct);

    List<Purchase> getProfitByDateRange(Date startDare, Date nowDate, Integer limit, Integer offset, String criterion);

    List<Product> purchaseProductsFromBasket(Client client, Purchase purchase);
}
