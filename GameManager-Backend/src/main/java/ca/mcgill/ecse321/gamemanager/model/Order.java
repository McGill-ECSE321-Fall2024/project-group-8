package ca.mcgill.ecse321.gamemanager.model;

import java.util.*;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Order
{
  @Id
  @GeneratedValue
  private int orderId;
  public enum OrderStatus { ShoppingCart, Bought }
  @ManyToOne
  private Customer customer;
  private OrderStatus orderStatus;
  private List<Game> order;
  private double totalPrice;
  private Date date;

  protected Order() {

  }

  public Order(int OrderId, Customer Customer, OrderStatus OrderStatus, double TotalPrice, Date Date)
  {
    this.orderId = OrderId;
    this.customer = Customer;
    this.orderStatus = OrderStatus;
    this.order = new ArrayList<Game>();
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
    Game aOrder = order.get(index);
    return aOrder;
  }

  public Game[] getOrder()
  {
    Game[] newOrder = order.toArray(new Game[order.size()]);
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