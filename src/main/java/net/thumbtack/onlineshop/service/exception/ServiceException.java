package net.thumbtack.onlineshop.service.exception;

import net.thumbtack.onlineshop.daoimpl.daoexception.DaoException;
import net.thumbtack.onlineshop.exception.AppErrorCode;
import net.thumbtack.onlineshop.exception.AppException;

public class ServiceException extends RuntimeException{

    private AppErrorCode errorCode;
    private String field;
    private String message;

    // REVU в классе должен быть только один конструктор, присваивающий значения полям
    // остальные должны вызывать его через this(параметры)
    public ServiceException(AppErrorCode errorCode, String field, String message) {
        this.errorCode = errorCode;
        this.field = field;
        this.message = message;
    }

    public ServiceException(String field) {
        this.errorCode = errorCode;
        this.field = field;
        this.message = message;
    }

    public ServiceException(AppException ex) {
        this.errorCode = ex.getErrorCode();
        this.field = ex.getField();
        this.message = ex.getMessage();
    }

    public ServiceException(AppErrorCode errorCode, String... values) {
        this.errorCode = errorCode;
        this.field = errorCode.getField();
        this.message = String.format(errorCode.getErrorString(), values);
    }

    public ServiceException(DaoException ex) {
        errorCode = ex.getErrorCode();
        field = ex.getField();
        message = ex.getMessage();
    }

    public AppErrorCode getErrorCode() {
        return errorCode;
    }

    public String getField() {
        return field;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
