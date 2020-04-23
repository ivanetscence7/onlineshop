package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.model.Category;

import java.util.List;

public interface CategoryDao {

    Category getCategoryById(int categoryId);

    Category updateCategory(Integer categoryId, Category category);

    void deleteCategory(int categoryId);

    List<Category> getCategories();

    Category addCategory(Category category);

    List<Category> getCategoriesByListId(List<Integer> category);
}
