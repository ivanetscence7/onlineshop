package net.thumbtack.onlineshop.dto.response;

import java.util.List;

public class FailedDtoResponse {
    private List<FailedItemDtoResponse> errors;

    public FailedDtoResponse() {
    }

    public List<FailedItemDtoResponse> getErrors() {
        return errors;
    }

    public FailedDtoResponse(List<FailedItemDtoResponse> errors) {
        this.errors = errors;
    }
}

