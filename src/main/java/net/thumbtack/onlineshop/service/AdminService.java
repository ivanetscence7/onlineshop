package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.daoimpl.AdminDaoImpl;
import net.thumbtack.onlineshop.dto.request.AdminDtoRequest;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.dto.request.UpdateAdminDtoRequest;
import net.thumbtack.onlineshop.dto.response.*;
import net.thumbtack.onlineshop.model.*;
import net.thumbtack.onlineshop.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static net.thumbtack.onlineshop.config.AppProperties.COOKIE_NAME;
import static net.thumbtack.onlineshop.exception.AppErrorCode.*;
import static net.thumbtack.onlineshop.model.UserType.ADMIN;

@Service
public class AdminService extends BaseService {

    private final AdminDaoImpl adminDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    public AdminService(AdminDaoImpl adminDao) {
        this.adminDao = adminDao;
    }

    public AdminDtoResponse adminRegistration(AdminDtoRequest adminDtoRequest, HttpServletResponse response) throws ServiceException {
        LOGGER.debug("Service registration Admin {}", adminDtoRequest);
        try {
            Admin admin = new Admin(adminDtoRequest.getFirstName(), adminDtoRequest.getLastName(), adminDtoRequest.getPatronymic(), adminDtoRequest.getLogin(), adminDtoRequest.getPassword(), ADMIN, adminDtoRequest.getPosition());

            Cookie cookie = new Cookie(COOKIE_NAME, UUID.randomUUID().toString());
            response.addCookie(cookie);

            admin = adminDao.adminRegistration(admin, cookie);

            return new AdminDtoResponse(admin.getId(), admin.getFirstName(), admin.getLastName(), admin.getPatronymic(), admin.getPosition());
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public AdminDtoResponse updateAdmin(UpdateAdminDtoRequest updateAdminDtoRequest, String token) {
        LOGGER.debug("Service update Admin {}", updateAdminDtoRequest);
        try {
            Admin admin = getAdminByToken(token);

            validUserPassword(admin.getPassword(), updateAdminDtoRequest.getOldPassword());

            changeAdminFields(admin, updateAdminDtoRequest);

            adminDao.updateAdmin(admin);

            return new AdminDtoResponse(admin.getId(), admin.getFirstName(), admin.getLastName(),
                    admin.getPatronymic(), admin.getPosition());
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    private Admin getAdminByToken(String token) {
        Admin admin = adminDao.getAdminByToken(token);
        if (admin == null) {
            throw new ServiceException(WRONG_TOKEN, token);
        }
        if (admin.getUserType() != ADMIN) {
            throw new ServiceException(PERMISSION_DENIED);
        }
        return admin;
    }

    private void changeAdminFields(Admin admin, UpdateAdminDtoRequest request) {
        admin.setFirstName(request.getFirstName());
        admin.setLastName(request.getLastName());
        admin.setPatronymic(request.getPatronymic());
        admin.setPosition(request.getPosition());
        admin.setPassword(request.getNewPassword());
    }
}
