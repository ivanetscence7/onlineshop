package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.daoimpl.AdminDaoImpl;
import net.thumbtack.onlineshop.daoimpl.ClientDaoImpl;
import net.thumbtack.onlineshop.daoimpl.UserDaoImpl;
import net.thumbtack.onlineshop.dto.request.LoginDtoRequest;
import net.thumbtack.onlineshop.dto.response.EmptyResponse;
import net.thumbtack.onlineshop.dto.response.InputAdminDtoResponse;
import net.thumbtack.onlineshop.dto.response.InputClientDtoResponse;
import net.thumbtack.onlineshop.dto.response.InputUserResponse;
import net.thumbtack.onlineshop.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static net.thumbtack.onlineshop.config.Config.COOKIE_NAME;
import static net.thumbtack.onlineshop.model.UserType.ADMIN;
import static net.thumbtack.onlineshop.model.UserType.CLIENT;

@Service
public class UserService {

    private final UserDaoImpl userDao;
    private final AdminDaoImpl adminDao;
    private final ClientDaoImpl clientDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserDaoImpl userDao, AdminDaoImpl adminDao, ClientDaoImpl clientDao) {
        this.userDao = userDao;
        this.adminDao = adminDao;
        this.clientDao = clientDao;
    }

    public InputUserResponse login(LoginDtoRequest req, HttpServletResponse response) {
        LOGGER.debug("Service user login ");
        try {
            UUID token = UUID.randomUUID();
            Cookie cookie = new Cookie(COOKIE_NAME, token.toString());
            response.addCookie(cookie);
            User user = userDao.login(req.getLogin(), req.getPassword());
            if (user.getUserType() == ADMIN) {
                Admin admin = adminDao.getAdmin(user);
                Session session = new Session(admin, cookie.getValue());
                userDao.addSession(session);
                return new InputAdminDtoResponse(admin.getId(), admin.getFirstName(), admin.getLastName(), admin.getPatronymic(), admin.getPosition());
            } else if (user.getUserType() == CLIENT) {
                Client client = clientDao.getClient(user);
                Session session = new Session(client, cookie.getValue());
                userDao.addSession(session);
                return new InputClientDtoResponse(client.getId(), client.getFirstName(), client.getLastName(), client.getPatronymic(), client.getEmail(),
                        client.getAddress(),
                        client.getPhone(),
                        client.getDeposit().getDeposit()); //исправить
            }
            // REVU create you own exception class and throw it in DAO 
            // and do not catch, let GlobalExceptionHandler catches them
        } catch (RuntimeException ex) {
            LOGGER.info("Service can't  login User {}, {}", ex);
        }
        return null;
    }


    public EmptyResponse logout(String cookie) {
        LOGGER.debug("Service user login Cookie{} ", cookie);
        try {
            userDao.logout(cookie);
            return new EmptyResponse();
        } catch (RuntimeException ex) {
            LOGGER.info("Service can't  logout User with Cookie{} {}", cookie, ex);
        }
        return null;
    }

    public InputUserResponse getInfoAboutCurUser(String token, HttpServletResponse response) {
        User user = getUserByToken(token);
        if (user.getUserType() == CLIENT) {
            response.addCookie(new Cookie(COOKIE_NAME, token));
            return new InputClientDtoResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getPatronymic(),
                    ((Client) user).getEmail(), ((Client) user).getAddress(), ((Client) user).getPhone(),
                    ((Client) user).getDeposit().getDeposit());
        } else if (user.getUserType() == ADMIN) {
            response.addCookie(new Cookie(COOKIE_NAME, token));
            return new InputAdminDtoResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getPatronymic(), ((Admin) user).getPosition());
        }
        return null;
    }

    private User getUserByToken(String token) {
        return userDao.getUserByToken(token);
    }
}
