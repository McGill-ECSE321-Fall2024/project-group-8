package ca.mcgill.ecse321.gamemanager.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

import jakarta.persistence.*;
import java.util.*;

// line 85 "model.ump"
// line 167 "model.ump"
@Entity
public class Wishlist {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Wishlist Attributes
    @Id
    private int wishlistId;

    @OneToMany
    private List<Game> wishlist;

    //Wishlist Associations
    @OneToOne
    private CustomerWishlist customerWishlist;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    protected Wishlist() {
    }

    public Wishlist(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    //------------------------
    // GETTERS AND SETTERS
    //------------------------

    public CustomerWishlist getCustomerWishlist() {
        return customerWishlist;
    }

    public void setCustomerWishlist(CustomerWishlist customerWishlist) {
        this.customerWishlist = customerWishlist;
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public boolean setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
        return true;
    }

    public boolean addGame(Game game) {
        return wishlist.add(game);
    }

    public boolean removeGame(Game game) {
        return wishlist.remove(game);
    }

    public List<Game> getGames() {
        return Collections.unmodifiableList(wishlist);
    }

    public void delete() {
        wishlist.clear();
    }

    @Override
    public String toString() {
        return "Wishlist[" +
                "wishlistId=" + wishlistId +
                ']';
    }
}
