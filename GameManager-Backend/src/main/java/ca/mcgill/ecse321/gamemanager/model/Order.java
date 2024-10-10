package ca.mcgill.ecse321.gamemanager.model;

import java.util.*;
import java.sql.Date;

import jakarta.persistence.*;

@Entity
public class Order {
    @Id
    @GeneratedValue
    private int orderId;

    public enum OrderStatus { ShoppingCart, Bought }

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<Game> games;

    private double totalPrice;
    private Date date;

    protected Order() {
    }

    public Order(int OrderId, Customer Customer, OrderStatus OrderStatus, double TotalPrice, Date Date)
    {
        this.orderId = OrderId;
        this.customer = Customer;
        this.orderStatus = OrderStatus;
        this.totalPrice = TotalPrice;
        this.date = Date;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public OrderStatus getOrderStatus()
    {
        return orderStatus;
    }

    public Game getOrder(int index)
    {
        Game aOrder = games.get(index);
        return aOrder;
    }

    public Game[] getOrder()
    {
        Game[] newOrder = games.toArray(new Game[games.size()]);
        return newOrder;
    }

    public double getTotalPrice()
    {
        return totalPrice;
    }

    public Date getDate()
    {
        return date;
}
}