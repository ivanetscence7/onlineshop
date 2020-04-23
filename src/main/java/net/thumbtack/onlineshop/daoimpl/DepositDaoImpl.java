package net.thumbtack.onlineshop.daoimpl;

import net.thumbtack.onlineshop.dao.DepositDao;
import net.thumbtack.onlineshop.exception.AppErrorCode;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.model.Deposit;
import net.thumbtack.onlineshop.mybatis.mappers.DepositMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static net.thumbtack.onlineshop.exception.AppErrorCode.WRONG_VERSION_FOR_UPDATE;

@Component
public class DepositDaoImpl implements DepositDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepositDaoImpl.class);
    private DepositMapper depositMapper;

    @Autowired
    public DepositDaoImpl(DepositMapper depositMapper) {
        this.depositMapper = depositMapper;
    }

    @Transactional
    public Deposit putDeposit(int id, Deposit deposit) {
        LOGGER.debug("DAO update deposit Deposit {}", deposit);
        try {
            checkSuccessPutDeposit(depositMapper.putDeposit(id, deposit));
            return deposit;
        } catch (RuntimeException ex) {
            LOGGER.error("Can't update deposit Deposit {} {}", deposit, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    private void checkSuccessPutDeposit(int resultUpdate) {
        if (resultUpdate != 1) {
            throw new DaoException(WRONG_VERSION_FOR_UPDATE);
        }
    }

    public Deposit getDepositById(int id) {
        LOGGER.debug("DAO get deposit dy Id {}", id);
        try {
            return depositMapper.getDepositById(id);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get deposit dy Id {} {}", id, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }


}
