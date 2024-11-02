package ca.mcgill.ecse321.gamemanager.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import java.sql.Date;
import java.util.*;
import jakarta.persistence.*;
// line 58 "model.ump"
// line 148 "model.ump"
@Entity
@Table(name = "purchase_order")
public class PurchaseOrder
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum OrderStatus { ShoppingCart, Bought }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PurchaseOrder Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int orderId;
  private OrderStatus orderStatus;
  private double totalPrice;
  private Date date;

  //PurchaseOrder Associations
  private List<GameCopy> gameCopies;
  private Customer created;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  @SuppressWarnings("unused")
  protected PurchaseOrder(){}

  public PurchaseOrder(OrderStatus aOrderStatus, double aTotalPrice, Date aDate, Customer aCreated)
  {
    orderStatus = aOrderStatus;
    totalPrice = aTotalPrice;
    date = aDate;
    gameCopies = new ArrayList<GameCopy>();
    boolean didAddCreated = setCreated(aCreated);
    if (!didAddCreated)
    {
      throw new RuntimeException("Unable to create purchaseOrder due to created. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  /* Code from template association_GetOne */
  public Customer getCreated()
  {
    return created;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGameCopies()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public GameCopy addGameCopy(Game aGame)
  {
    return new GameCopy(aGame);
  }

  public boolean addGameCopy(GameCopy aGameCopy)
  {
    boolean wasAdded = false;
    if (gameCopies.contains(aGameCopy)) { return false; }
    PurchaseOrder existingPurchaseOrder = aGameCopy.getPurchaseOrder();
    boolean isNewPurchaseOrder = existingPurchaseOrder != null && !this.equals(existingPurchaseOrder);
    if (isNewPurchaseOrder)
    {
      aGameCopy.setPurchaseOrder(this);
    }
    else
    {
      gameCopies.add(aGameCopy);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGameCopy(GameCopy aGameCopy)
  {
    boolean wasRemoved = false;
    //Unable to remove aGameCopy, as it must always have a purchaseOrder
    if (!this.equals(aGameCopy.getPurchaseOrder()))
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
  /* Code from template association_SetOneToMany */
  public boolean setCreated(Customer aCreated)
  {
    boolean wasSet = false;
    if (aCreated == null)
    {
      return wasSet;
    }

    Customer existingCreated = created;
    created = aCreated;
    if (existingCreated != null && !existingCreated.equals(aCreated))
    {
      existingCreated.removePurchaseOrder(this);
    }
    created.addPurchaseOrder(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=gameCopies.size(); i > 0; i--)
    {
      GameCopy aGameCopy = gameCopies.get(i - 1);
      aGameCopy.delete();
    }
    Customer placeholderCreated = created;
    this.created = null;
    if(placeholderCreated != null)
    {
      placeholderCreated.removePurchaseOrder(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "orderId" + ":" + getOrderId()+ "," +
            "totalPrice" + ":" + getTotalPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "orderStatus" + "=" + (getOrderStatus() != null ? !getOrderStatus().equals(this)  ? getOrderStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "created = "+(getCreated()!=null?Integer.toHexString(System.identityHashCode(getCreated())):"null");
  }
}