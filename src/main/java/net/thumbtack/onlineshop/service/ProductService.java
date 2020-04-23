package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.daoimpl.CategoryDaoImpl;
import net.thumbtack.onlineshop.daoimpl.ProductDaoImpl;
import net.thumbtack.onlineshop.dto.request.ProductDtoRequest;
import net.thumbtack.onlineshop.dto.request.UpdateProductDtoRequest;
import net.thumbtack.onlineshop.dto.response.*;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.order.Order;
import net.thumbtack.onlineshop.exception.ServiceException;
import net.thumbtack.onlineshop.model.Category;
import net.thumbtack.onlineshop.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static net.thumbtack.onlineshop.exception.AppErrorCode.WRONG_OR_EMPTY_REQUEST_PARAM;

@Service
public class ProductService extends BaseService {

    private ProductDaoImpl productDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    public ProductService(ProductDaoImpl productDao) {
        this.productDao = productDao;
    }

    public ProductDtoResponse addProduct(ProductDtoRequest productDtoRequest, String token) {
        LOGGER.debug("Service add Product {}", productDtoRequest);
        try {
            validAdmin(token);

            Product product = new Product(productDtoRequest.getName(), productDtoRequest.getPrice(), productDtoRequest.getCount());
            if (productDtoRequest.getCategories() != null) {
                product.setCategories(getCategoriesByListId(productDtoRequest.getCategories()));
            }

            product = productDao.addProduct(product);

            if (product.getCategories() != null) {
                return new ProductDtoResponse(product.getId(), product.getName(), product.getPrice(), product.getCount(),
                        product.getCategories().stream().map(Category::getId).collect(Collectors.toList()));
            } else {
                return new ProductDtoResponse(product.getId(), product.getName(), product.getPrice(), product.getCount());
            }
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }


    public ProductDtoResponse updateProduct(int productId, String token, UpdateProductDtoRequest request) {
        LOGGER.debug("Service update product by Id {}", productId);
        try {
            validAdmin(token);

            Product product = getProductById(productId);

            changeProductFields(product, request);

            product = productDao.updateProduct(product);

            return new ProductDtoResponse(product.getId(), product.getName(), product.getPrice(), product.getCount(),
                    product.getCategories().stream().map(Category::getId).collect(Collectors.toList()));
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public EmptyResponse deleteProduct(int productId, String token) {
        LOGGER.debug("Service delete product by Id {}", productId);
        try {
            validAdmin(token);
            productDao.deleteProduct(productId);
            return new EmptyResponse();
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public ProductWithCategoryNameDtoResponse getInfoAboutProduct(int productId, String token) {
        LOGGER.debug("Service get info about product by Id {}", productId);
        try {
            validUser(token);

            Product product = productDao.getProductById(productId);

            return new ProductWithCategoryNameDtoResponse(product.getId(), product.getName(), product.getPrice(), product.getCount(),
                    Collections.singletonList(product.getCategories().stream()
                            .map(Category::getName).collect(Collectors.joining(",")))
            );
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public AllProductsDtoResponse getProductsByParams(List<Integer> category, String order, String token) {
        LOGGER.debug("Service get products by params {}, {}", category, order);
        try {
            validUser(token);

            Set<Product> products = new TreeSet<>(Comparator.comparing(Product::getName));
            products.addAll(productDao.getAllProductsByParams(category, order));

            return productsByParams(products, category, order);

        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    private void changeProductFields(Product product, UpdateProductDtoRequest request) {
        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getCount() != null) {
            product.setCount(request.getCount());
        }
        if (request.getCategories() != null) {
            product.getCategories().clear();
            product.getCategories().addAll(getCategoriesByListId(request.getCategories()));
        }
    }

    protected Product getProductById(int productId) {
        try {
            return productDao.getProductById(productId);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    private AllProductsDtoResponse productsByParams(Set<Product> products, List<Integer> category, String order) {
        if (category != null || order.equals(Order.PRODUCT.getOrder())) {
            return productsByOrderProductAndCategoryList(products);
        } else if (order.equals(Order.CATEGORY.getOrder())) {
            return productsByOrderCategory(products);
        }
        throw new ServiceException(WRONG_OR_EMPTY_REQUEST_PARAM);
    }

    private AllProductsDtoResponse productsByOrderCategory(Set<Product> products) {

        AllProductsDtoResponse response = new AllProductsDtoResponse();
        Set<ProductWithAboveCategoryNameDtoResponse> categoryNameDtoResponse = new TreeSet<>(Comparator.comparing(ProductWithAboveCategoryNameDtoResponse::getCategoryName));

        Set<ProductDtoResponse> productDtoResponses = new TreeSet<>(Comparator.comparing(ProductDtoResponse::getName));
        ProductWithAboveCategoryNameDtoResponse withAboveCategoryNameDtoResponse = new ProductWithAboveCategoryNameDtoResponse();

        products.forEach(product -> {
            if (product.getCategories().size() == 0) {
                sortedProductWithoutCategory(product, productDtoResponses, withAboveCategoryNameDtoResponse, categoryNameDtoResponse);
            } else {
                sortedProductWithCategory(product, categoryNameDtoResponse);
            }
        });
        response.setResultsByOrderCategory(categoryNameDtoResponse);
        return response;
    }

    private AllProductsDtoResponse productsByOrderProductAndCategoryList(Set<Product> products) {
        return new AllProductsDtoResponse(products.stream()
                .map(product -> {
                    return new ProductWithCategoryNameDtoResponse(product.getId(), product.getName(), product.getPrice(), product.getCount(),
                            Collections.singletonList(product.getCategories().stream()
                                    .map(Category::getName).collect(Collectors.joining(",")))
                    );
                }).collect(Collectors.toList()));
    }

    private void sortedProductWithCategory(Product product, Set<ProductWithAboveCategoryNameDtoResponse> categoryNameDtoResponse) {
        product.getCategories().forEach(category -> {
            categoryNameDtoResponse.add(new ProductWithAboveCategoryNameDtoResponse(category.getName(),
                    category.getProducts().stream()
                            .map(pr -> new ProductDtoResponse(pr.getId(), pr.getName(), pr.getPrice(), pr.getCount()))
                            .sorted(Comparator.comparing(ProductDtoResponse::getName))
                            .collect(Collectors.toCollection(LinkedHashSet::new))
            ));
        });
    }

    private void sortedProductWithoutCategory(Product product, Set<ProductDtoResponse> productDtoResponses, ProductWithAboveCategoryNameDtoResponse withAboveCategoryNameDtoResponse, Set<ProductWithAboveCategoryNameDtoResponse> categoryNameDtoResponse) {
        productDtoResponses.add(new ProductDtoResponse(product.getId(), product.getName(), product.getPrice(), product.getCount()));
        withAboveCategoryNameDtoResponse.setProducts(productDtoResponses);
        categoryNameDtoResponse.add(withAboveCategoryNameDtoResponse);
    }

}
