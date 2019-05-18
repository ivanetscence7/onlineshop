package net.thumbtack.onlineshop.dto.response;

import java.util.List;

public class AllErrorResponse {

    List<ErrorResponse> errors;

    public AllErrorResponse() {
    }

    public AllErrorResponse(List<ErrorResponse> errors) {
        this.errors = errors;
    }

    public List<ErrorResponse> getErrors() {
        return errors;
    }
}
