package net.thumbtack.onlineshop.model;

import java.util.Objects;

public class Session {

    private User user;
    private String cookie;

    public Session(User user, String cookie) {
        this.user = user;
        this.cookie = cookie;
    }

    public User getUser() {
        return user;
    }

    public String getCookie() {
        return cookie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(getUser(), session.getUser()) &&
                Objects.equals(getCookie(), session.getCookie());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUser(), getCookie());
    }

    @Override
    public String toString() {
        return "Session{" +
                "user=" + user +
                ", cookie='" + cookie + '\'' +
                '}';
    }
}
