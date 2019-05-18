package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.model.*;

import javax.servlet.http.Cookie;
import java.util.List;

public interface AdminDao {

    User adminRegistration(Admin user, Cookie cookie);

    Admin getAdmin(User user);

    Product addProduct(Product product);

    List<Integer> setCategories(int id, List<Integer> categories);

    User getAdminByLogin(String login);

    User getAdminByToken(String token);

    List<Client> getAllClients(UserType type);

    User updateAdmin(User admin);

    Category addCategory(Category category);

    Category getCategoryById(int categoryId);

    Category updateCategory(Integer categoryId, Category category);

    void deleteCategory(int categoryId);

    List<Category> getCategories();

    Product updateProduct(Product product, List<Integer> categories);

    List<Integer> updateProductCategories(int productId, List<Integer> categories);

    Product getProductById(int productId);
}
