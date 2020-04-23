package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.model.Product;

import java.util.List;
import java.util.Set;

public interface ProductDao{

    Product addProduct(Product product);

    List<Integer> updateProductCategories(int productId, List<Integer> categories);

    Product updateProduct(Product product);

    Product getProductById(int productId);

    void deleteProduct(int productId);

    List<Product> getAllProductsByParams(List<Integer> category, String order);

    List<Product> getProductsByListId(List<Integer> product);
}
