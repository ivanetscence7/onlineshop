package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.daoimpl.AdminDaoImpl;
import net.thumbtack.onlineshop.daoimpl.daoexception.DaoException;
import net.thumbtack.onlineshop.dto.request.CategoryDtoRequest;
import net.thumbtack.onlineshop.dto.request.ProductDtoRequest;
import net.thumbtack.onlineshop.dto.request.RegistrationAdminDtoRequest;
import net.thumbtack.onlineshop.dto.request.UpdateAdminDtoRequest;
import net.thumbtack.onlineshop.dto.response.*;
import net.thumbtack.onlineshop.model.*;
import net.thumbtack.onlineshop.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static net.thumbtack.onlineshop.config.Config.COOKIE_NAME;
import static net.thumbtack.onlineshop.model.UserType.ADMIN;

@Transactional
@Service
public class AdminService {

    private final AdminDaoImpl adminDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    public AdminService(AdminDaoImpl adminDao) {
        this.adminDao = adminDao;
    }

    public InputAdminDtoResponse adminRegistration(RegistrationAdminDtoRequest req, HttpServletResponse response) throws ServiceException {
        try {
            User user = new Admin(req.getFirstName(), req.getLastName(), req.getPatronymic(), req.getLogin(), req.getPassword(), ADMIN, req.getPosition());
            UUID token = UUID.randomUUID();
            Cookie cookie = new Cookie(COOKIE_NAME, token.toString());
            response.addCookie(cookie);
            user = adminDao.adminRegistration((Admin) user, cookie);
            return new InputAdminDtoResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getPatronymic(), ((Admin) user).getPosition());
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    private Admin getAdminByToken(String token) {
        User admin = adminDao.getAdminByToken(token);
        return (Admin) admin;
    }

    public List<InfoAboutClientDtoResponse> getInfoAboutClients(String token) {
        try {
            User admin = adminDao.getAdminByToken(token);
            if (admin.getUserType() == ADMIN && (admin != null)) {
                List<Client> clients = adminDao.getAllClients(UserType.CLIENT);
                return new ArrayList<InfoAboutClientDtoResponse>(
                        clients.stream()
                                .map(client -> new InfoAboutClientDtoResponse(client.getId(),
                                        client.getFirstName(),
                                        client.getLastName(),
                                        client.getPatronymic(),
                                        client.getEmail(),
                                        client.getAddress(),
                                        client.getPhone(),
                                        client.getUserType()))
                                .collect(Collectors.toList()));
            }
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
        // REVU why ? what does it mean ?
        return null;
    }

    public InputAdminDtoResponse updateAdmin(UpdateAdminDtoRequest request, String token) {
        User admin = getAdminByToken(token);
        if (admin.getPassword().equals(request.getOldPassword())) {
            admin.setFirstName(request.getFirstName());
            admin.setLastName(request.getLastName());
            admin.setPatronymic(request.getPatronymic());
            ((Admin) admin).setPosition(request.getPosition());
            admin.setPassword(request.getNewPassword());
            admin = adminDao.updateAdmin(admin);
        }
        return new InputAdminDtoResponse(admin.getId(), admin.getFirstName(), admin.getLastName(),
                admin.getPatronymic(), ((Admin) admin).getPosition());
    }

    public CategoryDtoResponse addCategory(CategoryDtoRequest req, String token) {
        User user = getAdminByToken(token);
        if (user.getUserType() == ADMIN) {
            Category category = new Category(req.getParentId(), req.getName());
            category = adminDao.addCategory(category);
            if (category.getParent() == null) {
                return new CategoryDtoResponse(category.getId(), category.getName());
            } else {
                return new CategoryDtoResponse(category.getId(), category.getName(),
                        category.getParent().getId(), category.getParent().getName());
            }
        }
        return null;
    }

    public CategoryDtoResponse updateCategory(int categoryId, CategoryDtoRequest request, String token) {
        User user = getAdminByToken(token);
        if (user.getUserType() == ADMIN) {
            Category category = getCategoryById(categoryId);
            if(request.getName() != null) {
                category.setName(request.getName());
            }else{
                category.setId(null);
            }
            if(request.getParentId() != null) {
                category.setId(request.getParentId());
            }else{
                category.setId(null);
            }
            category = adminDao.updateCategory(categoryId,category);
            if (category.getParent() == null) {
                return new CategoryDtoResponse(category.getId(), category.getName());
            } else {
                return new CategoryDtoResponse(category.getId(), category.getName(),
                        category.getParent().getId(), category.getParent().getName());
            }
        }
        return null;
    }

    public CategoryDtoResponse getCategoryById(int categoryId, String token) {
        User user = getAdminByToken(token);
        if (user.getUserType() == ADMIN) {
            Category category = adminDao.getCategoryById(categoryId);
            if (category.getParent() == null) {
                return new CategoryDtoResponse(category.getId(), category.getName());
            } else {
                return new CategoryDtoResponse(category.getId(), category.getName(),
                        category.getParent().getId(), category.getParent().getName());
            }
        }
        return null;
    }

    private Category getCategoryById(int categoryId) {
        return adminDao.getCategoryById(categoryId);
    }

    public EmptyResponse deleteCategory(int categoryId, String token) {
        User user = getAdminByToken(token);
        if (user.getUserType() == ADMIN) {
            adminDao.deleteCategory(categoryId);
        }
        return new EmptyResponse();
    }

    public List<Category> getCategories(String token) {
        User user = getAdminByToken(token);
        if (user.getUserType() == ADMIN) {
            List<Category> categories = adminDao.getCategories();
            Predicate<Category> notNull = p -> (p.getParent() != null);
//            return new ArrayList<CategoryDtoResponse>(
//                    categories.stream()
//                            .map(category -> new CategoryDtoResponse(category.getId(),
//                                    category.getName(),
//                                    category.getParent().getId(),
//                                    category.getParent().getName()))
//                                    .collect(Collectors.toList()));

//                return new ArrayList<Category>(
//                        categories.stream()
//                        .map()
//                )
            return categories;
        }
        return null;
    }

    public ProductDtoResponse addProduct(ProductDtoRequest request, String token) {
        User user = adminDao.getAdminByToken(token);
        if (user.getUserType() == ADMIN) {
            Product product = new Product(request.getName(), request.getPrice(), request.getCount());
            List<Integer> categories = request.getCategories();
            List<Integer> list = new ArrayList<>();
            product = adminDao.addProduct(product);
            list = adminDao.setCategories(product.getId(), categories);
            return new ProductDtoResponse(product.getId(), product.getName(), product.getPrice(), product.getCount(), list);
        }
        return null;
    }

    public ProductDtoResponse updateProduct(int productId, String token, ProductDtoRequest request) {
        User user = getAdminByToken(token);
        if (user.getUserType() == ADMIN) {
            Product product = getProductById(productId);
            if (request.getName() != null) {
                product.setName(request.getName());
            }
            if (request.getPrice() != null) {
                product.setPrice(request.getPrice());
            }
            if (request.getCount() != null) {
                product.setCount(request.getCount());
            }
                        //Product product = new Product(request.getName(), request.getPrice(), request.getCount());
            List<Integer> categories = new ArrayList<>(request.getCategories());
            product = adminDao.updateProduct(product, categories);

            //categories = adminDao.deleteProductCategories(productId, categories);
            return new ProductDtoResponse(product.getId(), product.getName(), product.getPrice(),product.getCount(), categories);
        }
        return null;
    }

    private Product getProductById(int productId) {
        return adminDao.getProductById(productId);
    }
}
