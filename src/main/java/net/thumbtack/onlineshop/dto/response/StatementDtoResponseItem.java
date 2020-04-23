package net.thumbtack.onlineshop.dto.response;

import java.util.List;

public class StatementDtoResponseItem extends StatementDtoResponse {

    private List<InfoAboutClientDtoResponse> clients;

    public StatementDtoResponseItem() {
    }

    public StatementDtoResponseItem(List<InfoAboutClientDtoResponse> clients) {
        this.clients = clients;
    }

    public List<InfoAboutClientDtoResponse> getClients() {
        return clients;
    }
}
