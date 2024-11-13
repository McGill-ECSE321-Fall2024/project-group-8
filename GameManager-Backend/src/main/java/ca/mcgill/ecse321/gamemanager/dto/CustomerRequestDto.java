package ca.mcgill.ecse321.gamemanager.dto;

import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder;

import java.util.ArrayList;
import java.util.List;

public class CustomerRequestDto {
    private String name;
    private String email;
    private String password;
    private List<PurchaseOrder> purchaseOrders;
    private List<Game> inWishlist;
    private List<Game> inCart;

    public CustomerRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        purchaseOrders = new ArrayList<>();
        inWishlist = new ArrayList<>();
        inCart = new ArrayList<>();
    }
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {return password;}

    public void sePassword(String password) {
        this.password = password;
    }
    public void setPassword(String password) {this.password = password;}

}
