package net.thumbtack.onlineshop.dto.request;

public class BuyProductDtoRequest {

    private Integer id;
    private String name;
    private Integer price;
    private Integer count;

    public BuyProductDtoRequest(Integer id, String name, Integer price, Integer count) {
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
