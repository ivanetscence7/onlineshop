package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.daoimpl.ClientDaoImpl;
import net.thumbtack.onlineshop.daoimpl.daoexception.DaoException;
import net.thumbtack.onlineshop.dto.request.AddProductToBasketDtoRequest;
import net.thumbtack.onlineshop.dto.request.RegistrationClientDtoRequest;
import net.thumbtack.onlineshop.dto.request.UpdateClientDtoRequest;
import net.thumbtack.onlineshop.dto.response.AddProductToBasketDtoResponse;
import net.thumbtack.onlineshop.dto.response.InputClientDtoResponse;
import net.thumbtack.onlineshop.model.*;
import net.thumbtack.onlineshop.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static net.thumbtack.onlineshop.config.Config.COOKIE_NAME;
import static net.thumbtack.onlineshop.model.UserType.CLIENT;

@Service
public class ClientService {

    private final ClientDaoImpl clientDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    public ClientService(ClientDaoImpl clientDao) {
        this.clientDao = clientDao;
    }

    public InputClientDtoResponse clientRegistration(RegistrationClientDtoRequest req, HttpServletResponse response) {

        Deposit deposit = new Deposit(0);
        User user = new Client(req.getFirstName(), req.getLastName(), req.getPatronymic(), req.getLogin(), req.getPassword(),
                CLIENT, req.getEmail(), req.getAddress(), req.getPhone(), deposit);

        LOGGER.debug("Service registration Client {}", user);
        try {
            UUID token = UUID.randomUUID();
            Cookie cookie = new Cookie(COOKIE_NAME, token.toString());
            response.addCookie(cookie);

            user = clientDao.clientRegistration((Client) user, cookie);

            return new InputClientDtoResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getPatronymic(),
                    ((Client) user).getEmail(), ((Client) user).getAddress(), ((Client) user).getPhone(), ((Client) user).getDeposit().getDeposit());

        } catch (RuntimeException ex) {
            LOGGER.info("Service can't  registration Client {}, {}", user, ex);
        }
        return null;
    }


    public List<AddProductToBasketDtoResponse> addProductToBasket(AddProductToBasketDtoRequest request, String token) {
        try {
            User client = clientDao.getClientByToken(token);
            if (client.getUserType() == CLIENT && (client != null)) {
                Product product = new Product(request.getId(), request.getName(), request.getPrice(), request.getCount());
                //Basket basket = new Basket(product);
                clientDao.addProductToBasket((Client) client, product);
                Basket basket = clientDao.getAllProductsInBasket(client);
                return new ArrayList<AddProductToBasketDtoResponse>(
                        basket.getProducts().stream()
                                .map(prod -> new AddProductToBasketDtoResponse(
                                        prod.getId(),
                                        prod.getName(),
                                        prod.getPrice(),
                                        prod.getCount()))
                                .collect(Collectors.toList())
                );
            }
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
        return null;
    }

    public InputClientDtoResponse updateClient(UpdateClientDtoRequest request, String token) {
        User client = getClientByToken(token);
        if (client.getPassword().equals(request.getOldPassword())) {
            client.setFirstName(request.getFirstName());
            client.setLastName(request.getLastName());
            client.setPatronymic(request.getPatronymic());
            ((Client) client).setEmail(request.getEmail());
            ((Client) client).setAddress(request.getAddress());
            ((Client) client).setPhone(request.getPhone());
            client.setPassword(request.getNewPassword());
            client = clientDao.updateClient(client);
        }return new InputClientDtoResponse(client.getId(),client.getFirstName(),client.getLastName(),client.getPatronymic(),
                ((Client)client).getEmail(),((Client)client).getAddress(),((Client)client).getPhone(),((Client)client).getDeposit().getDeposit());
    }

    private User getClientByToken(String token) {
        User client = clientDao.getUserByToken(token);
        return (Client) client;
    }
}
