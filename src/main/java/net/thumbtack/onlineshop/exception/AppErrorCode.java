package net.thumbtack.onlineshop.exception;

public enum AppErrorCode {

    LOGIN_ALREADY_EXISTS("User %s already exists", "login"),
    WRONG_TOKEN("Wrong token", "token");

    String errorString;
    String field;

    AppErrorCode(String errorString, String field) {
        this.errorString = errorString;
        this.field = field;
    }

    public String getErrorString() {
        return errorString;
    }

    public String getField() {
        return field;
    }
}
