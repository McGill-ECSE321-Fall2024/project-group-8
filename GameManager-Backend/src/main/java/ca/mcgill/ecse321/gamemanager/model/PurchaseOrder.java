/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/
package ca.mcgill.ecse321.gamemanager.model;


import java.sql.Date;
import java.util.*;

import jakarta.persistence.*;

// line 60 "model.ump"
// line 144 "model.ump"

@Entity
@Table(name = "purchase_order")
public class PurchaseOrder
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum OrderStatus { ShoppingCart, Delivered }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int orderId;
  private OrderStatus orderStatus;
  private double totalPrice;
  private Date date;

  //Order Associations
  @OneToMany(mappedBy = "purchaseOrder", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<GameCopy> gameCopies = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
        name = "customer_email",
        foreignKey = @ForeignKey(name = "CUSTOMER_EMAIL_FK")
  )
  private Customer buyer;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  @SuppressWarnings("unused")
  protected PurchaseOrder(){}

  public PurchaseOrder(int aOrderId, OrderStatus aOrderStatus, double aTotalPrice, Date aDate, Customer aBuyer)
  {
    orderId = aOrderId;
    orderStatus = aOrderStatus;
    totalPrice = aTotalPrice;
    date = aDate;
    gameCopies = new ArrayList<GameCopy>();
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

  public boolean setOrderStatus(OrderStatus aOrderStatus)
  {
    boolean wasSet = false;
    orderStatus = aOrderStatus;
    wasSet = true;
    return wasSet;
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
    return buyer;
  }

  public OrderStatus getOrderStatus()
  {
    return orderStatus;
  }

  public double getTotalPrice()
  {
    return totalPrice;
  }

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetMany */
  public GameCopy getGameCopy(int index)
  {
    GameCopy aGameCopy = gameCopies.get(index);
    return aGameCopy;
  }

  public List<GameCopy> getGameCopies()
  {
    List<GameCopy> newGameCopies = Collections.unmodifiableList(gameCopies);
    return newGameCopies;
  }

  public int numberOfGameCopies()
  {
    int number = gameCopies.size();
    return number;
  }

  public boolean hasGameCopies()
  {
    boolean has = gameCopies.size() > 0;
    return has;
  }

  public int indexOfGameCopy(GameCopy aGameCopy)
  {
    int index = gameCopies.indexOf(aGameCopy);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGameCopies()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addGameCopy(GameCopy aGameCopy)
  {
    boolean wasAdded = false;
    if (gameCopies.contains(aGameCopy)) { return false; }
    gameCopies.add(aGameCopy);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGameCopy(GameCopy aGameCopy)
  {
    boolean wasRemoved = false;
    if (gameCopies.contains(aGameCopy))
    {
      gameCopies.remove(aGameCopy);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGameCopyAt(GameCopy aGameCopy, int index)
  {  
    boolean wasAdded = false;
    if(addGameCopy(aGameCopy))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGameCopies()) { index = numberOfGameCopies() - 1; }
      gameCopies.remove(aGameCopy);
      gameCopies.add(index, aGameCopy);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGameCopyAt(GameCopy aGameCopy, int index)
  {
    boolean wasAdded = false;
    if(gameCopies.contains(aGameCopy))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGameCopies()) { index = numberOfGameCopies() - 1; }
      gameCopies.remove(aGameCopy);
      gameCopies.add(index, aGameCopy);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGameCopyAt(aGameCopy, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    gameCopies.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "orderId" + ":" + getOrderId()+ "," +
            "totalPrice" + ":" + getTotalPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "buyer" + "=" + (getCustomer() != null ? !getCustomer().equals(this)  ? getCustomer().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "orderStatus" + "=" + (getOrderStatus() != null ? !getOrderStatus().equals(this)  ? getOrderStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}


