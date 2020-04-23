package net.thumbtack.onlineshop.daoimpl;

import net.thumbtack.onlineshop.dao.ClientDao;
import net.thumbtack.onlineshop.exception.AppErrorCode;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.model.Client;
import net.thumbtack.onlineshop.model.UserType;
import net.thumbtack.onlineshop.mybatis.mappers.ClientMapper;
import net.thumbtack.onlineshop.mybatis.mappers.SessionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.util.List;

@Component
public class ClientDaoImpl implements ClientDao {

    private ClientMapper clientMapper;
    private SessionMapper sessionMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDaoImpl.class);

    @Autowired
    public ClientDaoImpl(ClientMapper clientMapper, SessionMapper sessionMapper) {
        this.clientMapper = clientMapper;
        this.sessionMapper = sessionMapper;
    }

    @Transactional
    @Override
    public Client clientRegistration(Client client, Cookie cookie) {
        LOGGER.debug("DAO registration  Client {}", client);
        try {
            sessionMapper.insertUser(client);
            clientMapper.insertClient(client);
            sessionMapper.insertSession(client, cookie);
            clientMapper.insertDeposit(client);
            clientMapper.insertBasket(client);
        } catch (RuntimeException ex) {
            LOGGER.error("DAO can't insert Client {}, {}", client, ex);
            throw new DaoException(AppErrorCode.LOGIN_ALREADY_EXISTS, client.getLogin());
        }
        return client;
    }

    @Transactional
    @Override
    public void updateClient(Client client) {
        LOGGER.debug("DAO update client Client {}", client);
        try {
            sessionMapper.updateUser(client);
            clientMapper.updateClient(client);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't update client Client {} {}", client, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public List<Client> getAllClients(UserType type) {
        LOGGER.debug("DAO get all clients by UserType {}", type);
        try {
            return clientMapper.getAllClients(type);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get all clients by UserType {} {}", type, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public Client getClientsWithPurchasesById(Integer clientId) {
        LOGGER.debug("DAO get all clients by Id {}", clientId);
        try {
            return clientMapper.getClientById(clientId);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get all clients by Id {} {}", clientId, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public Client getClientByToken(String token) {
        LOGGER.debug("DAO get client by token {}", token);
        try {
            return (Client) sessionMapper.getUserByToken(token);
        } catch (RuntimeException ex) {
            LOGGER.error("Can't get all clients by UserType {} {}", token, ex);
            throw new DaoException(AppErrorCode.DATABASE_ERROR);
        }
    }
}
