package net.thumbtack.onlineshop.dto.response;

public class BuyProductDtoResponse {

    private Integer id;
    private String name;
    private Integer price;
    private Integer count;

    public BuyProductDtoResponse(Integer id, String name, Integer price, Integer count) {
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
