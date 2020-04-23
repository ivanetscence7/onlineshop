package net.thumbtack.onlineshop.service;


import net.thumbtack.onlineshop.daoimpl.CategoryDaoImpl;
import net.thumbtack.onlineshop.daoimpl.SessionDaoImpl;
import net.thumbtack.onlineshop.dto.response.AdminDtoResponse;
import net.thumbtack.onlineshop.dto.response.ClientDtoResponse;
import net.thumbtack.onlineshop.dto.response.UserDtoResponse;
import net.thumbtack.onlineshop.exception.AppErrorCode;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.exception.ServiceException;
import net.thumbtack.onlineshop.model.Admin;
import net.thumbtack.onlineshop.model.Category;
import net.thumbtack.onlineshop.model.Client;
import net.thumbtack.onlineshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

import static net.thumbtack.onlineshop.exception.AppErrorCode.*;
import static net.thumbtack.onlineshop.model.UserType.ADMIN;
import static net.thumbtack.onlineshop.model.UserType.CLIENT;

@Component
public class BaseService {

    @Autowired
    private SessionDaoImpl userDao;

    @Autowired
    private CategoryDaoImpl categoryDao;


    protected void validAdmin(String token) {
        User user = getUserByToken(token);
        checkIsAdmin(user);
    }

    protected void validClient(String token) {
        User user = getUserByToken(token);
        checkIsClient(user);
    }

    protected void checkIsAdmin(User user) {
        if (user.getUserType() != ADMIN) {
            throw new ServiceException(PERMISSION_DENIED);
        }
    }

    protected void checkIsClient(User user) {
        if (user.getUserType() != CLIENT) {
            throw new ServiceException(PERMISSION_DENIED);
        }
    }

    protected User getUserByToken(String token) {
        User user = userDao.getUserByToken(token);
        if (user.getUserType() != CLIENT && user.getUserType() != ADMIN) {
            throw new ServiceException(PERMISSION_DENIED);
        }
        return user;
    }

    protected void validUserPassword(String password, String oldPassword) {
        if (!password.equals(oldPassword)) {
            throw new ServiceException(WRONG_PASSWORD);
        }
    }

    protected String getNormalPhoneNumber(String phone) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(phone.split("-")).forEach(sb::append);
        return sb.toString();
    }

    protected void validUser(String token) {
        User user = getUserByToken(token);
        if (user.getUserType() != CLIENT && user.getUserType() != ADMIN) {
            throw new ServiceException(PERMISSION_DENIED);
        }
    }

    private boolean listOfCategoriesMatches(List<Category> categoryList, List<Integer> listCategoryId) {
        List<Integer> nums = new ArrayList<>();
        categoryList.forEach(category -> nums.add(category.getId()));
        return nums.size() == listCategoryId.size();
    }

    protected List<Category> getCategoriesByListId(List<Integer> listCategoryId) {
        try {
            List<Category> categoryList = categoryDao.getCategoriesByListId(listCategoryId);
            if (listOfCategoriesMatches(categoryList, listCategoryId)) {
                return categoryList;
            } else {
                throw new ServiceException(CATEGORY_LIST_ERROR);
            }
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    protected List<Category> sortedCategoryAndSubCategoryByName(List<Category> categories) {
        try {
            SortedMap<Category, List<Category>> mainCategoryToListSubCategory =
                    new TreeMap<>(Comparator.comparing(Category::getName));

            categories.forEach(category -> {
                if (category.getParent() == null) {
                    mainCategoryToListSubCategory.put(category, new ArrayList<>());
                } else {
                    if (mainCategoryToListSubCategory.get(category.getParent()) == null) {
                        mainCategoryToListSubCategory.put(category.getParent(), new ArrayList(Collections.singleton(category)));
                    } else {
                        mainCategoryToListSubCategory.get(category.getParent()).add(category);
                    }
                }
            });

            return sortedCategoriesByNameResult(mainCategoryToListSubCategory);

        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    private List<Category> sortedCategoriesByNameResult(SortedMap<Category, List<Category>> mainCategoryToListSubCategory) {
        List<Category> resultList = new ArrayList<>();
        mainCategoryToListSubCategory.forEach((key, value) -> {
            resultList.add(key);
            resultList.addAll(value);
        });
        return resultList;
    }

    protected UserDtoResponse checkWhoAndBring(User user) {
        if (user.getUserType() == ADMIN) {
            Admin admin = (Admin) user;
            return new AdminDtoResponse(admin.getId(), admin.getFirstName(),
                    admin.getLastName(), admin.getPatronymic(), admin.getPosition());
        }
        if (user.getUserType() == CLIENT) {
            Client client = (Client) user;
            return new ClientDtoResponse(client.getId(), client.getFirstName(), client.getLastName(), client.getPatronymic(),
                    client.getEmail(), client.getAddress(), client.getPhone(), client.getDeposit().getAmount());
        }
        throw new ServiceException(AppErrorCode.USER_NOT_FOUND);
    }


}
