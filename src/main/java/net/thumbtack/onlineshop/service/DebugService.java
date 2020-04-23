package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.dao.DebugDao;
import net.thumbtack.onlineshop.dto.response.EmptyResponse;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DebugService {

    private DebugDao debugDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(DebugService.class);

    @Autowired
    public DebugService(DebugDao debugDao) {
        this.debugDao = debugDao;
    }

    public EmptyResponse clearDataBase() {
        LOGGER.debug("Service clear database");
        try {
            debugDao.clearDataBase();
            return new EmptyResponse();
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }
}
