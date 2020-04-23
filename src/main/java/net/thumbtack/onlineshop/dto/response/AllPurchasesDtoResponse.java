package net.thumbtack.onlineshop.dto.response;

import java.util.List;

public class AllPurchasesDtoResponse extends StatementDtoResponse {
    private int profit;
    private List<PurchaseDtoResponse> purchases;

    public AllPurchasesDtoResponse() {
    }

    public AllPurchasesDtoResponse(int profit, List<PurchaseDtoResponse> purchases) {
        this.profit = profit;
        this.purchases = purchases;
    }

    public AllPurchasesDtoResponse(List<PurchaseDtoResponse> purchases) {
        this.purchases = purchases;
    }

    public List<PurchaseDtoResponse> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseDtoResponse> purchases) {
        this.purchases = purchases;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }
}
