package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.daoimpl.SessionDaoImpl;
import net.thumbtack.onlineshop.dto.request.LoginDtoRequest;
import net.thumbtack.onlineshop.dto.response.*;
import net.thumbtack.onlineshop.exception.AppErrorCode;
import net.thumbtack.onlineshop.exception.DaoException;
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

@Service
public class SessionService extends BaseService {

    private final SessionDaoImpl userDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionService.class);

    @Autowired
    public SessionService(SessionDaoImpl userDao) {
        this.userDao = userDao;
    }

    public UserDtoResponse login(LoginDtoRequest loginDtoRequest, HttpServletResponse response) {
        LOGGER.debug("Service user login {}",loginDtoRequest );
        try {
            String login = loginDtoRequest.getLogin();
            User user = userDao.getUserByLogin(login);
            if (user == null) {
                throw new ServiceException(AppErrorCode.WRONG_LOGIN);
            }
            if (!verifyUserLoginAndPassword(loginDtoRequest, user)) {
                throw new ServiceException(AppErrorCode.WRONG_LOGIN);
            }
            String token = UUID.randomUUID().toString();

            userDao.insertToken(token, user);
            response.addCookie(new Cookie(COOKIE_NAME, token));

            return checkWhoAndBring(user);
        } catch (DaoException ex) {
            LOGGER.info("Can't login User with login {} {}",loginDtoRequest.getLogin(), ex);
            throw new ServiceException(ex);
        }

    }


    public EmptyResponse logout(String token, HttpServletResponse response) {
        LOGGER.debug("Service user logout by token{} ", token);
        try {
            Cookie cookie = new Cookie(COOKIE_NAME, null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            userDao.logout(token);
            return new EmptyResponse();
        } catch (DaoException ex) {
            LOGGER.info("Can't logout User with Cookie{} {}", token, ex);
            throw new ServiceException(ex);
        }
    }

    private boolean verifyUserLoginAndPassword(LoginDtoRequest req, User user) {
        return user.getLogin().equalsIgnoreCase(req.getLogin()) && user.getPassword().equals(req.getPassword());
    }

}
