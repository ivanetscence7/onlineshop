package net.thumbtack.onlineshop.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfoAboutAllClientDtoResponse {

    private List<InfoAboutClientDtoResponse> result;

    public InfoAboutAllClientDtoResponse() {
        result = new ArrayList<>();
    }

    public InfoAboutAllClientDtoResponse(List<InfoAboutClientDtoResponse> result) {
        this.result = result;
    }

    public List<InfoAboutClientDtoResponse> getResult() {
        return result;
    }
}
