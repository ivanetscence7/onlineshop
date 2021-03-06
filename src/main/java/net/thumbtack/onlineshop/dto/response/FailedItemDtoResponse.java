package net.thumbtack.onlineshop.dto.response;

import net.thumbtack.onlineshop.exception.AppErrorCode;

public class FailedItemDtoResponse {
    private AppErrorCode errorCode;
    private String field;
    private String message;

    public FailedItemDtoResponse() {
    }

    public FailedItemDtoResponse(AppErrorCode errorCode, String field, String message) {
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

    public String getMessage() {
        return message;
    }
}
