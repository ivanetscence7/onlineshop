package net.thumbtack.onlineshop.daoimpl;

import net.thumbtack.onlineshop.dao.AdminDao;
import net.thumbtack.onlineshop.daoimpl.daoexception.DaoException;
import net.thumbtack.onlineshop.exception.AppErrorCode;
import net.thumbtack.onlineshop.model.*;
import net.thumbtack.onlineshop.mybatis.mappers.AdminMapper;
import net.thumbtack.onlineshop.mybatis.mappers.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.List;

@Component
public class AdminDaoImpl implements AdminDao {

    private AdminMapper adminMapper;
    private UserMapper userMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDaoImpl.class);

    @Autowired
    public AdminDaoImpl(AdminMapper adminMapper, UserMapper userMapper) {
        this.adminMapper = adminMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Admin adminRegistration(Admin user, Cookie cookie) {
        LOGGER.debug("DAO admin registration Admin{}", user);
        try {
            userMapper.insertUser(user);
            adminMapper.insertToAdmin(user);
            userMapper.insertToSession(user, cookie);
        } catch (RuntimeException ex) {
            LOGGER.info("DAO can't insert Admin {}, {}", user, ex);
            throw new DaoException(AppErrorCode.LOGIN_ALREADY_EXISTS, user.getLogin());
        }
        return user;
    }

    @Override
    public Admin getAdmin(User user) {
        return adminMapper.getAdminById(user);
    }

    @Override
    public User getAdminByLogin(String login) {
        return userMapper.getUserByLogin(login);
    }

    @Override
    public User getAdminByToken(String token) {
        return userMapper.getUserByToken(token);
    }

    @Override
    public List<Client> getAllClients(UserType type) {
        return adminMapper.getAllClients(type);
    }

    @Override
    public User updateAdmin(User admin) {
        userMapper.updateUser(admin);
        adminMapper.updateAdmin(admin);
        return admin;
    }

    @Override
    public Category addCategory(Category category) {
        if (category.getId() == 0) {
            adminMapper.addCategory(category);
            return adminMapper.getCategoryWithSubCategory(category.getId());
        } else {
            adminMapper.addSubCategory(category);
            return adminMapper.getCategoryWithSubCategory(category.getId());
        }
    }

    @Override
    public Category getCategoryById(int categoryId) {
        return adminMapper.getCategoryWithSubCategory(categoryId);
    }

    @Override
    public Category updateCategory(Integer categoryId, Category category) {
//        if (category.getId() == null) {
//            adminMapper.updateCategory(category);
//        } else {
//            adminMapper.updateSubCategory( category);
//        }
//        return adminMapper.getCategoryWithSubCategory(categoryId);
        Category cot = category;
        adminMapper.updateMyRoyalCategory(categoryId, category);
        Category cat = category;
        return adminMapper.getCategoryWithSubCategory(categoryId);
    }

    @Override
    public void deleteCategory(int categoryId) {
        adminMapper.deleteCategoryById(categoryId);
    }


    @Override
    public List<Category> getCategories() {
        List<Category> list = adminMapper.getCategories();
        return list;
    }

    @Override
    public Product updateProduct(Product product, List<Integer> categories) {
        adminMapper.updateProduct(product);
        if (categories != null) {
            adminMapper.deleteProductCategories(product);
            categories.stream().forEach(c -> adminMapper.setCategories(product.getId(), c));
        }
        product.setCategories(adminMapper.getProductById(product.getId()).getCategories());
        return product;
    }

    @Override
    public List<Integer> updateProductCategories(int productId, List<Integer> categories) {
        return null;
    }

    @Override
    public Product getProductById(int productId) {
        return adminMapper.getProductById(productId);
    }

    @Override
    public Product addProduct(Product product) {
        adminMapper.addProduct(product);
        return product;
    }

    @Override
    public List<Integer> setCategories(int id, List<Integer> categories) {
        if (categories != null) {
            categories.stream().forEach(c -> adminMapper.setCategories(id, c));
            return categories;
        }
        return null;
    }
}
