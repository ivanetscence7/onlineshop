package net.thumbtack.onlineshop.daoimpl;

import net.thumbtack.onlineshop.dao.CategoryDao;
import net.thumbtack.onlineshop.exception.AppErrorCode;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.model.Category;
import net.thumbtack.onlineshop.mybatis.mappers.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static net.thumbtack.onlineshop.exception.AppErrorCode.NAME_CATEGORY_ALREADY_EXISTS;
import static net.thumbtack.onlineshop.exception.AppErrorCode.PARENT_ID_ERROR;

@Component
public class CategoryDaoImpl implements CategoryDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryDaoImpl.class);
    private CategoryMapper categoryMapper;

    @Autowired
    public CategoryDaoImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Category addCategory(Category category) {
        LOGGER.debug("DAO add category Category {}", category);
        try {
            categoryMapper.addCategory(category);
            return getCategoryById(category.getId());
        } catch (RuntimeException ex) {
            LOGGER.error("Can't add category Category {} {}", category, ex);
            throw new DaoException(AppErrorCode.NAME_CATEGORY_ALREADY_EXISTS);
        }
    }

    @Override
    public List<Category> getCategoriesByListId(List<Integer> category) {
        LOGGER.debug("DAO get category with subcategory by list Id{}", category);
        try {
           return categoryMapper.getCategoriesByListId(category);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get category with subcategory by list Id {} {}", category, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }


    @Override
    public Category getCategoryById(int categoryId) {
        LOGGER.debug("DAO get category with subcategory by Id{}", categoryId);
        try {
            return categoryMapper.getCategoryWithSubCategory(categoryId);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get category with subcategory by Id {} {}", categoryId, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public Category updateCategory(Integer categoryId, Category category) {
        LOGGER.debug("DAO update category Category{}", category);
        try {
            categoryMapper.updateCategory(categoryId, category);
            return categoryMapper.getCategoryWithSubCategory(categoryId);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't update category Category{} {}", category, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public void deleteCategory(int categoryId) {
        LOGGER.debug("DAO delete category by d{}", categoryId);
        try {
            categoryMapper.deleteCategoryById(categoryId);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't delete category by Id{} {}", categoryId, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public List<Category> getCategories() {
        LOGGER.debug("DAO get categories ");
        try {
            return categoryMapper.getCategories();
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get categories {}", ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }
}
