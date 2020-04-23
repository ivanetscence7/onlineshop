package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.daoimpl.DepositDaoImpl;
import net.thumbtack.onlineshop.dto.request.DepositDtoRequest;
import net.thumbtack.onlineshop.dto.response.ClientDtoResponse;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.exception.ServiceException;
import net.thumbtack.onlineshop.model.Client;
import net.thumbtack.onlineshop.model.Deposit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepositService extends BaseService {

    private final DepositDaoImpl depositDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(DepositService.class);

    @Autowired
    public DepositService(DepositDaoImpl depositDao) {
        this.depositDao = depositDao;
    }

    public ClientDtoResponse putDeposit(DepositDtoRequest depositDtoRequest, String token) {
        LOGGER.debug("Service put Deposit {}", depositDtoRequest);
        try {
            Client client = (Client) getUserByToken(token);
            checkIsClient(client);

            Deposit deposit = client.getDeposit();
            int resultDeposit = deposit.getAmount() + depositDtoRequest.getDeposit();
            client.getDeposit().setAmount(resultDeposit);

            deposit = depositDao.putDeposit(client.getId(), client.getDeposit());

            return new ClientDtoResponse(client.getId(), client.getFirstName(), client.getLastName(), client.getPatronymic(),
                    client.getEmail(), client.getAddress(), client.getPhone(), deposit.getAmount());
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public ClientDtoResponse getClientDeposit(String token) {
        try {
            Client client = (Client) getUserByToken(token);
            checkIsClient(client);

            return new ClientDtoResponse(client.getId(), client.getFirstName(), client.getLastName(), client.getPatronymic(),
                    client.getEmail(), client.getAddress(), client.getPhone(), client.getDeposit().getAmount());
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }


}
