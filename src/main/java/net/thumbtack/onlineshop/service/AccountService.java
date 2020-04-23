package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.dto.response.AdminDtoResponse;
import net.thumbtack.onlineshop.dto.response.ClientDtoResponse;
import net.thumbtack.onlineshop.dto.response.UserDtoResponse;
import net.thumbtack.onlineshop.exception.AppErrorCode;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.exception.ServiceException;
import net.thumbtack.onlineshop.model.Admin;
import net.thumbtack.onlineshop.model.Client;
import net.thumbtack.onlineshop.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import static net.thumbtack.onlineshop.model.UserType.ADMIN;
import static net.thumbtack.onlineshop.model.UserType.CLIENT;

@Service
public class AccountService extends BaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    public UserDtoResponse getInfoAboutCurUser(String token) {
        LOGGER.debug("Service get info about current user by token {}",token);
        try {
            User user = getUserByToken(token);
            if (user != null) {
                return checkWhoAndBring(user);
            }
            throw new ServiceException(AppErrorCode.USER_NOT_FOUND);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }


}
