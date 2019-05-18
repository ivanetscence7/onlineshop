package net.thumbtack.onlineshop.daoimpl.daoexception;

import net.thumbtack.onlineshop.exception.AppErrorCode;

public class DaoException extends RuntimeException{

    private AppErrorCode errorCode;
    private String field;
    private String message;

    public DaoException(AppErrorCode errorCode, String field, String message) {
        this.errorCode = errorCode;
        this.field = field;
        this.message = message;
    }

    public DaoException(AppErrorCode errorCode) {
        this.errorCode = errorCode;
        this.field = errorCode.getField();
        this.message = errorCode.getErrorString();
    }

    public DaoException(AppErrorCode errorCode, String value) {
        this.errorCode = errorCode;
        this.field = errorCode.getField();
        this.message = String.format(errorCode.getErrorString(), value);
    }


    public AppErrorCode getErrorCode() {
        return errorCode;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
