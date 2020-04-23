package net.thumbtack.onlineshop.dto.response;


import java.util.Date;

public class PurchaseDtoResponse {

    private int id;
    private Date date;
    private int purchaseAmount;

    public PurchaseDtoResponse() {
    }


    public PurchaseDtoResponse(int id, Date date, int purchaseAmount) {
        this.id = id;
        this.date = date;
        this.purchaseAmount = purchaseAmount;
    }

    public Date getDate() {
        return date;
    }

    public int getPurchaseAmount() {
        return purchaseAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPurchaseAmount(int purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }
}

