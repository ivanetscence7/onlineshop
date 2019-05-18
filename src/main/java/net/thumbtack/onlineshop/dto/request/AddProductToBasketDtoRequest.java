package net.thumbtack.onlineshop.dto.request;

public class AddProductToBasketDtoRequest {

    private int id;
    private String name;
    private int price;
    private int count;

    public AddProductToBasketDtoRequest() {
    }

    public AddProductToBasketDtoRequest(Integer id, String name, int price, int count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getCount() {
        return count;
    }
}
