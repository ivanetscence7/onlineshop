package net.thumbtack.onlineshop.order;

public enum Order {
    PRODUCT("product"),
    CATEGORY("category");

    String order;

    Order(String order){
        this.order=order;
    }

    public String getOrder() {
        return order;
    }
}
