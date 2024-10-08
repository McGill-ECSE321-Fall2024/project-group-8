package ca.mcgill.ecse321.gamemanager.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.util.*;

// line 85 "model.ump"
// line 167 "model.ump"
@Entity
public class Wishlist
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Wishlist Attributes
  @Id
  private int wishlistId;
  @OneToOne
  private Customer customer;

  private List<Game> wishlist;
  @OneToOne
  //Wishlist Associations
  private Customer owns;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Wishlist(int aWishlistId, Customer aCustomer, Customer aOwns)
  {
    wishlistId = aWishlistId;
    customer = aCustomer;
    wishlist = new ArrayList<Game>();
    if (aOwns == null || aOwns.getWishlist() != null)
    {
      throw new RuntimeException("Unable to create Wishlist due to aOwns. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    owns = aOwns;
  }

  public Wishlist(int aWishlistId, Customer aCustomer, int aIdForOwns)
  {
    wishlistId = aWishlistId;
    customer = aCustomer;
    wishlist = new ArrayList<Game>();
    owns = new Customer(aIdForOwns, this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setWishlistId(int aWishlistId)
  {
    boolean wasSet = false;
    wishlistId = aWishlistId;
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
  /* Code from template attribute_SetMany */
  public boolean addWishlist(Game aWishlist)
  {
    boolean wasAdded = false;
    wasAdded = wishlist.add(aWishlist);
    return wasAdded;
  }

  public boolean removeWishlist(Game aWishlist)
  {
    boolean wasRemoved = false;
    wasRemoved = wishlist.remove(aWishlist);
    return wasRemoved;
  }

  public int getWishlistId()
  {
    return wishlistId;
  }

  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template attribute_GetMany */
  public Game getWishlist(int index)
  {
    Game aWishlist = wishlist.get(index);
    return aWishlist;
  }

  public Game[] getWishlist()
  {
    Game[] newWishlist = wishlist.toArray(new Game[wishlist.size()]);
    return newWishlist;
  }

  public int numberOfWishlist()
  {
    int number = wishlist.size();
    return number;
  }

  public boolean hasWishlist()
  {
    boolean has = wishlist.size() > 0;
    return has;
  }

  public int indexOfWishlist(Game aWishlist)
  {
    int index = wishlist.indexOf(aWishlist);
    return index;
  }
  /* Code from template association_GetOne */
  public Customer getOwns()
  {
    return owns;
  }

  public void delete()
  {
    Customer existingOwns = owns;
    owns = null;
    if (existingOwns != null)
    {
      existingOwns.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "wishlistId" + ":" + getWishlistId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "customer" + "=" + (getCustomer() != null ? !getCustomer().equals(this)  ? getCustomer().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "owns = "+(getOwns()!=null?Integer.toHexString(System.identityHashCode(getOwns())):"null");
  }
}