package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.model.*;

import javax.servlet.http.Cookie;
import java.util.List;

public interface AdminDao {

    Admin adminRegistration(Admin user, Cookie cookie);

    Admin getAdminByToken(String token);

    void updateAdmin(Admin admin);


}
