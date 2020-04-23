package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.daoimpl.CategoryDaoImpl;
import net.thumbtack.onlineshop.dto.request.CategoryDtoRequest;
import net.thumbtack.onlineshop.dto.request.UpdateCategoryDtoRequest;
import net.thumbtack.onlineshop.dto.response.AllCategoryDtoResponse;
import net.thumbtack.onlineshop.dto.response.CategoryDtoResponse;
import net.thumbtack.onlineshop.dto.response.EmptyResponse;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.exception.ServiceException;
import net.thumbtack.onlineshop.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static net.thumbtack.onlineshop.exception.AppErrorCode.*;

@Service
public class CategoryService extends BaseService {

    private CategoryDaoImpl categoryDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    public CategoryService(CategoryDaoImpl categoryDao) {
        this.categoryDao = categoryDao;
    }

    public CategoryDtoResponse addCategory(CategoryDtoRequest categoryDtoRequest, String token) {
        LOGGER.debug("Service add Category {}", categoryDtoRequest);
        try {
            validAdmin(token);

            Category category = new Category(categoryDtoRequest.getName());
            category = checkAndAddCategory(category, categoryDtoRequest.getParentId());

            if (category.getParent() == null) {
                return new CategoryDtoResponse(category.getId(), category.getName());
            } else {
                return new CategoryDtoResponse(category.getId(), category.getName(),
                        category.getParent().getId(), category.getParent().getName());
            }
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public CategoryDtoResponse updateCategory(int categoryId, UpdateCategoryDtoRequest request, String token) {
        LOGGER.debug("Service update category by Id {}", categoryId);
        try {
            validAdmin(token);

            Category category = getCategoryById(categoryId);

            checkAndChangeCategoryFields(request, category);

            category = categoryDao.updateCategory(categoryId, category);

            if (category.getParent() == null) {
                return new CategoryDtoResponse(category.getId(), category.getName());
            } else {
                return new CategoryDtoResponse(category.getId(), category.getName(),
                        category.getParent().getId(), category.getParent().getName());
            }
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public CategoryDtoResponse getCategoryById(int categoryId, String token) {
        LOGGER.debug("Service get category by Id {}", categoryId);
        try {
            validAdmin(token);

            Category category = getCategoryById(categoryId);

            if (category.getParent() == null) {
                return new CategoryDtoResponse(category.getId(), category.getName());
            } else {
                return new CategoryDtoResponse(category.getId(), category.getName(),
                        category.getParent().getId(), category.getParent().getName());
            }
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public EmptyResponse deleteCategory(int categoryId, String token) {
        LOGGER.debug("Service delete category by Id {}", categoryId);
        try {
            validAdmin(token);
            categoryDao.deleteCategory(categoryId);
            return new EmptyResponse();
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public AllCategoryDtoResponse getCategories(String token) {
        LOGGER.debug("Service get all categories by token {}", token);
        try {
            validAdmin(token);

            List<Category> categories = categoryDao.getCategories();

            List<Category> sortedCategoryByName = sortedCategoryAndSubCategoryByName(categories);

            return new AllCategoryDtoResponse(sortedCategoryByName.stream()
                    .map(category -> {
                                if (category.getParent() == null) {
                                    return new CategoryDtoResponse(category.getId(), category.getName());
                                } else {
                                    return new CategoryDtoResponse(category.getId(), category.getName(),
                                            category.getParent().getId(), category.getParent().getName());
                                }
                            }
                    ).collect(Collectors.toList()));
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    private Category checkAndAddCategory(Category category, Integer parentId) {
        if (parentId == null || parentId == 0 || checkParentCategory(parentId)) {
            if (parentId != null && parentId != 0) {
                Category parentCategory = getCategoryById(parentId);
                if (parentCategory != null) {
                    category.setParent(parentCategory);
                } else {
                    throw new ServiceException(NO_SUCH_CATEGORY_BY_PARENTID);
                }
            }
            category = categoryDao.addCategory(category);
            return category;
        } else {
            throw new ServiceException(SUBCATEGORY_ERROR);
        }
    }


    private boolean checkParentCategory(Integer id) {
        Category category = categoryDao.getCategoryById(id);
        if (category != null) {
            return category.getParent() == null;
        } else {
            throw new ServiceException(NO_SUCH_CATEGORY_BY_PARENTID);
        }
    }

    private void checkAndChangeCategoryFields(UpdateCategoryDtoRequest request, Category category) {
        if (category != null) {
            if (category.getParent() == null) {
                updateMainCategory(category, request);
            } else {
                updateSubCategory(category, request);
            }
        } else {
            throw new ServiceException(NO_SUCH_CATEGORY_BY_ID);
        }
    }

    private void updateSubCategory(Category category, UpdateCategoryDtoRequest request) {
        if (request.getParentId() != null && isNotCategoryForYourself(category, request)
                && isThereParentCategory(request.getParentId())
                && notSubCategoryForSubCategory(request.getParentId())) {
            if (request.getParentId() == 0) {
                throw new ServiceException(UPDATE_SUBCATEGORY_ERROR_PARENTID);
            } else {
                if (request.getName() != null) {
                    category.setName(request.getName());
                }
                category.getParent().setId(request.getParentId());
            }
        } else if (request.getName() != null) {
            category.setName(request.getName());
        }
    }

    private boolean notSubCategoryForSubCategory(Integer id) {
        Category category = getCategoryById(id);
        if (category.getParent() == null) {
            return true;
        } else {
            throw new ServiceException(SUBCATEGORY_ERROR);
        }
    }

    private void updateMainCategory(Category category, UpdateCategoryDtoRequest request) {
        if (request.getParentId() != null && isNotCategoryForYourself(category, request) && request.getParentId() != 0) {
            throw new ServiceException(UPDATE_CATEGORY_ERROR_PARENTID);
        } else if (request.getName() != null) {
            category.setName(request.getName());
        }
    }

    private boolean isThereParentCategory(Integer parentId) {
        if (categoryDao.getCategoryById(parentId) != null) {
            return true;
        } else {
            throw new ServiceException(PARENT_ID_ERROR);
        }
    }

    private Category getCategoryById(int id) {
        Category category = categoryDao.getCategoryById(id);
        if (category != null) {
            return category;
        } else {
            throw new DaoException(NO_SUCH_CATEGORY_BY_ID);
        }
    }

    private boolean isNotCategoryForYourself(Category category, UpdateCategoryDtoRequest request) {
        if (!request.getParentId().equals(category.getId())) {
            return true;
        } else {
            throw new ServiceException(UPDATE_CATEGORY_ERROR_PARENTID_EQUALS_ID);
        }
    }
}
