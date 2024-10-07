package ca.mcgill.ecse321.gamemanager.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;
import java.sql.Date;

// line 73 "model.ump"
// line 146 "model.ump"
public class Order
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum OrderStatus { ShoppingCart, Bought }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  private int orderId;
  private Customer customer;
  private OrderStatus orderStatus;
  private List<Game> order;
  private double totalPrice;
  private Date date;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(int aOrderId, Customer aCustomer, OrderStatus aOrderStatus, double aTotalPrice, Date aDate)
  {
    orderId = aOrderId;
    customer = aCustomer;
    orderStatus = aOrderStatus;
    order = new ArrayList<Game>();
    totalPrice = aTotalPrice;
    date = aDate;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOrderId(int aOrderId)
  {
    boolean wasSet = false;
    orderId = aOrderId;
    wasSet = true;
    return wasSet;
  }

  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    customer = aCustomer;
    wasSet = true;
    return wasSet;
  }

  public boolean setOrderStatus(OrderStatus aOrderStatus)
  {
    boolean wasSet = false;
    orderStatus = aOrderStatus;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetMany */
  public boolean addOrder(Game aOrder)
  {
    boolean wasAdded = false;
    wasAdded = order.add(aOrder);
    return wasAdded;
  }

  public boolean removeOrder(Game aOrder)
  {
    boolean wasRemoved = false;
    wasRemoved = order.remove(aOrder);
    return wasRemoved;
  }

  public boolean setTotalPrice(double aTotalPrice)
  {
    boolean wasSet = false;
    totalPrice = aTotalPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
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
  /* Code from template attribute_GetMany */
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

  public int numberOfOrder()
  {
    int number = order.size();
    return number;
  }

  public boolean hasOrder()
  {
    boolean has = order.size() > 0;
    return has;
  }

  public int indexOfOrder(Game aOrder)
  {
    int index = order.indexOf(aOrder);
    return index;
  }

  public double getTotalPrice()
  {
    return totalPrice;
  }

  public Date getDate()
  {
    return date;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "orderId" + ":" + getOrderId()+ "," +
            "totalPrice" + ":" + getTotalPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "customer" + "=" + (getCustomer() != null ? !getCustomer().equals(this)  ? getCustomer().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "orderStatus" + "=" + (getOrderStatus() != null ? !getOrderStatus().equals(this)  ? getOrderStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}