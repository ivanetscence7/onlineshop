package net.thumbtack.onlineshop.model;

import java.util.List;
import java.util.Objects;

public class Admin extends User {

    private String position;

    public Admin(String position) {
        this.position = position;
    }

    public Admin() {
    }

    public Admin(String firstName, String lastName, String patronymic, String login, String password, UserType userType, String position) {
        super(firstName, lastName, patronymic, login, password, userType);
        this.position = position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Admin admin = (Admin) o;
        return Objects.equals(getPosition(), admin.getPosition());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getPosition());
    }
}

