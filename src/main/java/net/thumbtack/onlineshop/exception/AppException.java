package net.thumbtack.onlineshop.exception;

public class AppException extends RuntimeException {

    private AppErrorCode errorCode;
    private String field;
    private String message;

    public AppException(AppErrorCode errorCode, String field, String message) {
        this.errorCode = errorCode;
        this.field = field;
        this.message = message;
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
