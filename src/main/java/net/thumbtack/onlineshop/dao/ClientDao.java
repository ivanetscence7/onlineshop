package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.model.*;

import javax.servlet.http.Cookie;
import java.util.List;

public interface ClientDao {

    User clientRegistration(Client user, Cookie cookie);

    User getClientByToken(String token);

    void updateClient(Client client);

    List<Client> getAllClients(UserType type);

    Client getClientsWithPurchasesById(Integer clientId);
}
