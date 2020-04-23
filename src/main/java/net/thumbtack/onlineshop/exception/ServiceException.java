package net.thumbtack.onlineshop.exception;

public class ServiceException extends RuntimeException {

    private AppErrorCode errorCode;
    private String field;
    private String message;

    public ServiceException(AppErrorCode errorCode, String field, String message) {
        this.errorCode = errorCode;
        this.field = field;
        this.message = message;
    }

    public ServiceException(AppErrorCode errorCode, String... values) {
        this(errorCode,errorCode.getField(),String.format(errorCode.getErrorString(), values));
    }

    public ServiceException(DaoException ex) {
        this(ex.getErrorCode(), ex.getField(), ex.getMessage());
    }

    public ServiceException(String field) {
        this.field = field;
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
