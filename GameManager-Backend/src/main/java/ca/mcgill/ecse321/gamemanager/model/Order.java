/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/
package ca.mcgill.ecse321.gamemanager.model;


import java.sql.Date;
import java.util.*;

import jakarta.persistence.*;

// line 60 "model.ump"
// line 144 "model.ump"

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id", "game_id"}))
// to ensure one customer can only have one review for each purchased game
public class Order
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
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<Game> games;
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
  protected Order(){}

  public Order(int aOrderId, OrderStatus aOrderStatus, double aTotalPrice, Date aDate, Customer aBuyer)
  {
    orderId = aOrderId;
    orderStatus = aOrderStatus;
    totalPrice = aTotalPrice;
    date = aDate;
    games = new ArrayList<Game>();
    boolean didAddBuyer = setBuyer(aBuyer);
    if (!didAddBuyer)
    {
      throw new RuntimeException("Unable to create order due to buyer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  public Game getGame(int index)
  {
    Game aGame = games.get(index);
    return aGame;
  }

  public List<Game> getGames()
  {
    List<Game> newGames = Collections.unmodifiableList(games);
    return newGames;
  }

  public int numberOfGames()
  {
    int number = games.size();
    return number;
  }

  public boolean hasGames()
  {
    boolean has = games.size() > 0;
    return has;
  }

  public int indexOfGame(Game aGame)
  {
    int index = games.indexOf(aGame);
    return index;
  }
  /* Code from template association_GetOne */
  public Customer getBuyer()
  {
    return buyer;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGames()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addGame(Game aGame)
  {
    boolean wasAdded = false;
    if (games.contains(aGame)) { return false; }
    games.add(aGame);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGame(Game aGame)
  {
    boolean wasRemoved = false;
    if (games.contains(aGame))
    {
      games.remove(aGame);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGameAt(Game aGame, int index)
  {
    boolean wasAdded = false;
    if(addGame(aGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGames()) { index = numberOfGames() - 1; }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGameAt(Game aGame, int index)
  {
    boolean wasAdded = false;
    if(games.contains(aGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGames()) { index = numberOfGames() - 1; }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    }
    else
    {
      wasAdded = addGameAt(aGame, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBuyer(Customer aBuyer)
  {
    boolean wasSet = false;
    if (aBuyer == null)
    {
      return wasSet;
    }

    Customer existingBuyer = buyer;
    buyer = aBuyer;
    if (existingBuyer != null && !existingBuyer.equals(aBuyer))
    {
      existingBuyer.removeOrder(this);
    }
    buyer.addOrder(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    games.clear();
    Customer placeholderBuyer = buyer;
    this.buyer = null;
    if(placeholderBuyer != null)
    {
      placeholderBuyer.removeOrder(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "orderId" + ":" + getOrderId()+ "," +
            "totalPrice" + ":" + getTotalPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "orderStatus" + "=" + (getOrderStatus() != null ? !getOrderStatus().equals(this)  ? getOrderStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "buyer = "+(getBuyer()!=null?Integer.toHexString(System.identityHashCode(getBuyer())):"null");
  }
}


