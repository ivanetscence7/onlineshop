package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.daoimpl.ClientDaoImpl;
import net.thumbtack.onlineshop.dto.request.ClientDtoRequest;
import net.thumbtack.onlineshop.dto.request.UpdateClientDtoRequest;
import net.thumbtack.onlineshop.dto.response.ClientDtoResponse;
import net.thumbtack.onlineshop.dto.response.InfoAboutAllClientDtoResponse;
import net.thumbtack.onlineshop.dto.response.InfoAboutClientDtoResponse;
import net.thumbtack.onlineshop.exception.DaoException;
import net.thumbtack.onlineshop.exception.ServiceException;
import net.thumbtack.onlineshop.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static net.thumbtack.onlineshop.config.AppProperties.COOKIE_NAME;
import static net.thumbtack.onlineshop.exception.AppErrorCode.PERMISSION_DENIED;
import static net.thumbtack.onlineshop.exception.AppErrorCode.WRONG_TOKEN;
import static net.thumbtack.onlineshop.model.UserType.CLIENT;

@Service
public class ClientService extends BaseService {

    private final ClientDaoImpl clientDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    public ClientService(ClientDaoImpl clientDao) {
        this.clientDao = clientDao;
    }

    public ClientDtoResponse clientRegistration(ClientDtoRequest clientDtoRequest, HttpServletResponse response) {
        LOGGER.debug("Service registration Client {}", clientDtoRequest);
        try {
            Client client = new Client(clientDtoRequest.getFirstName(), clientDtoRequest.getLastName(), clientDtoRequest.getPatronymic(), clientDtoRequest.getLogin(), clientDtoRequest.getPassword(),
                    CLIENT, clientDtoRequest.getEmail(), clientDtoRequest.getAddress(), getNormalPhoneNumber(clientDtoRequest.getPhone()));

            Cookie cookie = new Cookie(COOKIE_NAME, UUID.randomUUID().toString());
            response.addCookie(cookie);

            client = clientDao.clientRegistration(client, cookie);

            return new ClientDtoResponse(client.getId(), client.getFirstName(), client.getLastName(), client.getPatronymic(),
                    client.getEmail(), client.getAddress(), client.getPhone(), client.getDeposit().getAmount());
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    public ClientDtoResponse updateClient(UpdateClientDtoRequest updateClientDtoRequest, String token) {
        LOGGER.debug("Service update Client {}", updateClientDtoRequest);
        try {
            Client client = getClientByToken(token);

            validUserPassword(client.getPassword(), updateClientDtoRequest.getOldPassword());

            changeClientFields(client, updateClientDtoRequest);

            clientDao.updateClient(client);

            return new ClientDtoResponse(client.getId(), client.getFirstName(), client.getLastName(), client.getPatronymic(),
                    client.getEmail(), client.getAddress(), client.getPhone(), client.getDeposit().getAmount());
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }

    }

    public InfoAboutAllClientDtoResponse getInfoAboutClients(String token) {
        LOGGER.debug("Service get info about all clients by token {}", token);
        try {
            validAdmin(token);

            List<Client> clients = clientDao.getAllClients(CLIENT);

            return new InfoAboutAllClientDtoResponse(clients.stream()
                    .map(client -> new InfoAboutClientDtoResponse(client.getId(), client.getFirstName(), client.getLastName(), client.getPatronymic(),
                            client.getEmail(), client.getAddress(), client.getPhone(), client.getUserType())).collect(Collectors.toList()));
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
    }

    private void changeClientFields(Client client, UpdateClientDtoRequest request) {
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setPatronymic(request.getPatronymic());
        client.setEmail(request.getEmail());
        client.setAddress(request.getAddress());
        client.setPhone(getNormalPhoneNumber(request.getPhone()));
        client.setPassword(request.getNewPassword());
    }

    private Client getClientByToken(String token) {
        Client client = clientDao.getClientByToken(token);
        if (client == null) {
            throw new ServiceException(WRONG_TOKEN, token);
        }
        if (client.getUserType() != CLIENT) {
            throw new ServiceException(PERMISSION_DENIED);
        }
        return client;
    }
}
