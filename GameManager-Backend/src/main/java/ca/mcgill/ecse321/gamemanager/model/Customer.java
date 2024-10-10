package ca.mcgill.ecse321.gamemanager.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Customer extends Person {

    //------------------------
    // ENUMERATIONS
    //------------------------
    public enum OrderStatus {
        ShoppingCart, Bought
    }

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    // JPA requires a no-args constructor
    protected Customer() {
        super();
    }

    public Customer(String password, String name, String email) {
        super(password, name, email);
    }

    //------------------------
    // GETTERS AND SETTERS
    //------------------------

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    //------------------------
    // DELETE METHOD
    //------------------------

    @Override
    public void delete() {
        reviews.clear();
        orders.clear();
        super.delete();
    }
}
