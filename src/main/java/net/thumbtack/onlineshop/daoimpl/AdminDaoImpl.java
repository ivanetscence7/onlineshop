package net.thumbtack.onlineshop.daoimpl;

import net.thumbtack.onlineshop.dao.AdminDao;
import net.thumbtack.onlineshop.exception.AppErrorCode;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.model.Admin;
import net.thumbtack.onlineshop.mybatis.mappers.AdminMapper;
import net.thumbtack.onlineshop.mybatis.mappers.SessionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

@Component
public class AdminDaoImpl implements AdminDao {
    private AdminMapper adminMapper;
    private SessionMapper sessionMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDaoImpl.class);

    @Autowired
    public AdminDaoImpl(AdminMapper adminMapper, SessionMapper sessionMapper) {
        this.adminMapper = adminMapper;
        this.sessionMapper = sessionMapper;
    }

    @Transactional
    @Override
    public Admin adminRegistration(Admin admin, Cookie cookie) {
        LOGGER.debug("DAO admin registration Admin{}", admin);
        try {
            sessionMapper.insertUser(admin);
            adminMapper.insertAdmin(admin);
            sessionMapper.insertSession(admin, cookie);
        } catch (RuntimeException ex) {
            LOGGER.error("DAO can't insert Admin {}, {}", admin, ex);
            throw new DaoException(AppErrorCode.LOGIN_ALREADY_EXISTS);
        }
        return admin;
    }

    @Transactional
    @Override
    public void updateAdmin(Admin admin) {
        LOGGER.debug("DAO update admin Admin {}", admin);
        try {
            sessionMapper.updateUser(admin);
            adminMapper.updateAdmin(admin);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't update admin Admin {} {}", admin, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public Admin getAdminByToken(String token) {
        return (Admin) sessionMapper.getUserByToken(token);
    }

}
