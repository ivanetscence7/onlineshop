package net.thumbtack.onlineshop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.thumbtack.onlineshop.model.UserType;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfoAboutClientDtoResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private String address;
    private String phone;
    private UserType userType;
    private List<PurchaseDtoResponse> purchases;


    public InfoAboutClientDtoResponse() {
    }

    public InfoAboutClientDtoResponse(Integer id, String firstName, String lastName, String patronymic, String email, String address, String phone, UserType userType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.userType = userType;
    }

    public InfoAboutClientDtoResponse(Integer id, String firstName, String lastName, String patronymic, String email, String address, String phone,UserType userType, List<PurchaseDtoResponse> purchases) {
        this(id,firstName,lastName,patronymic,email,address,phone,userType);
        this.purchases = purchases;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public UserType getUserType() {
        return userType;
    }

    public boolean addPurchase(PurchaseDtoResponse purchaseDtoResponse){
        if(purchases != null){
            purchases.add(purchaseDtoResponse);
        }else{
            purchases = new ArrayList<>();
            purchases.add(purchaseDtoResponse);
        }
        return true;
    }

    public List<PurchaseDtoResponse> getPurchases() {
        return purchases;
    }

}
