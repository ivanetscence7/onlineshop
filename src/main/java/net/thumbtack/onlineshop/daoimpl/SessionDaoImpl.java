package net.thumbtack.onlineshop.daoimpl;

import net.thumbtack.onlineshop.dao.SessionDao;
import net.thumbtack.onlineshop.exception.AppErrorCode;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.model.User;
import net.thumbtack.onlineshop.mybatis.mappers.SessionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SessionDaoImpl implements SessionDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionDaoImpl.class);
    private SessionMapper sessionMapper;

    public SessionDaoImpl() {
    }

    @Autowired
    public SessionDaoImpl(SessionMapper sessionMapper) {
        this.sessionMapper = sessionMapper;
    }

    @Override
    public int logout(String token) {
        LOGGER.debug("DAO logout String {}", token);
        try {
            return sessionMapper.logout(token);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't logout String {} {}", token, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    @Transactional
    @Override
    public void insertToken(String token, User user) {
        LOGGER.debug("DAO insert token String {}", token);
        try {
            sessionMapper.deleteTokenByUserId(user.getId());
            sessionMapper.insertToken(user, token);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't insert token String {} {}", token, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }


    @Override
    public User getUserByToken(String token) {
        LOGGER.debug("DAO get user by token {}", token);
        try {
            return sessionMapper.getUserByToken(token);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get user by token {} {}", token, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public User getUserByLogin(String login) {
        LOGGER.debug("DAO get user by login {}", login);
        try {
            return sessionMapper.getUserByLogin(login);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get user by login {} {}", login, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }
}