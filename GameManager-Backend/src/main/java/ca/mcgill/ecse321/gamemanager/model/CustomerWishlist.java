package ca.mcgill.ecse321.gamemanager.model;

import jakarta.persistence.*;

// Mediating class between the bidirectional relationship between Customer and Wishlist

@Entity
public class CustomerWishlist {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Customer customer;

    @OneToOne
    private Wishlist wishlist;

    // No-args constructor
    protected CustomerWishlist() {
    }

    // Constructor for initializing the relationship
    public CustomerWishlist(Customer customer, Wishlist wishlist) {
        this.customer = customer;
        this.wishlist = wishlist;
    }

    // Getters and Setters
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }
}
