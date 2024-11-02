/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/
package ca.mcgill.ecse321.gamemanager.model;

import java.util.*;
import java.sql.Date;
import jakarta.persistence.*;

// line 12 "model.ump"
// line 99 "model.ump"
@Entity
public class Customer extends Person
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum GameStatus { Onsale, Available, Archived }
  public enum OrderStatus { ShoppingCart, Bought }
  public enum RequestStatus { PendingApproval, Approved, PendingArchived, Archived }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Associations
  @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews;
  @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PurchaseOrder> purchaseOrders;
  @OneToMany(mappedBy = "wishListOwner", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Game> inWishlist;
  @OneToMany(mappedBy = "cartOwner", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Game> inCart;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  @SuppressWarnings("unused")
  public Customer() {
    super();
  }
  public Customer(String aPassword, String aName, String aEmail)
  {
    super(aPassword, aName, aEmail);
    reviews = new ArrayList<Review>();
    purchaseOrders = new ArrayList<PurchaseOrder>();
    inWishlist = new ArrayList<Game>();
    inCart = new ArrayList<Game>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Review getReview(int index)
  {
    Review aReview = reviews.get(index);
    return aReview;
  }

  public List<Review> getReviews()
  {
    List<Review> newReviews = Collections.unmodifiableList(reviews);
    return newReviews;
  }

  public int numberOfReviews()
  {
    int number = reviews.size();
    return number;
  }

  public boolean hasReviews()
  {
    boolean has = reviews.size() > 0;
    return has;
  }

  public int indexOfReview(Review aReview)
  {
    int index = reviews.indexOf(aReview);
    return index;
  }
  /* Code from template association_GetMany */
  public PurchaseOrder getPurchaseOrder(int index)
  {
    PurchaseOrder aPurchaseOrder = purchaseOrders.get(index);
    return aPurchaseOrder;
  }

  public List<PurchaseOrder> getPurchaseOrders()
  {
    List<PurchaseOrder> newPurchaseOrders = Collections.unmodifiableList(purchaseOrders);
    return newPurchaseOrders;
  }

  public int numberOfPurchaseOrders()
  {
    int number = purchaseOrders.size();
    return number;
  }

  public boolean hasPurchaseOrders()
  {
    boolean has = purchaseOrders.size() > 0;
    return has;
  }

  public int indexOfPurchaseOrder(PurchaseOrder aPurchaseOrder)
  {
    int index = purchaseOrders.indexOf(aPurchaseOrder);
    return index;
  }
  /* Code from template association_GetMany */
  public Game getInWishlist(int index)
  {
    Game aInWishlist = inWishlist.get(index);
    return aInWishlist;
  }

  public List<Game> getInWishlist()
  {
    List<Game> newInWishlist = Collections.unmodifiableList(inWishlist);
    return newInWishlist;
  }

  public int numberOfInWishlist()
  {
    int number = inWishlist.size();
    return number;
  }

  public boolean hasInWishlist()
  {
    boolean has = inWishlist.size() > 0;
    return has;
  }

  public int indexOfInWishlist(Game aInWishlist)
  {
    int index = inWishlist.indexOf(aInWishlist);
    return index;
  }
  /* Code from template association_GetMany */
  public Game getInCart(int index)
  {
    Game aInCart = inCart.get(index);
    return aInCart;
  }

  public List<Game> getInCart()
  {
    List<Game> newInCart = Collections.unmodifiableList(inCart);
    return newInCart;
  }

  public int numberOfInCart()
  {
    int number = inCart.size();
    return number;
  }

  public boolean hasInCart()
  {
    boolean has = inCart.size() > 0;
    return has;
  }

  public int indexOfInCart(Game aInCart)
  {
    int index = inCart.indexOf(aInCart);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReviews()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addReview(Review aReview)
  {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) { return false; }
    reviews.add(aReview);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReview(Review aReview)
  {
    boolean wasRemoved = false;
    if (reviews.contains(aReview))
    {
      reviews.remove(aReview);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addReviewAt(Review aReview, int index)
  {  
    boolean wasAdded = false;
    if(addReview(aReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReviewAt(Review aReview, int index)
  {
    boolean wasAdded = false;
    if(reviews.contains(aReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addReviewAt(aReview, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPurchaseOrders()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addPurchaseOrder(PurchaseOrder aPurchaseOrder)
  {
    boolean wasAdded = false;
    if (purchaseOrders.contains(aPurchaseOrder)) { return false; }
    purchaseOrders.add(aPurchaseOrder);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePurchaseOrder(PurchaseOrder aPurchaseOrder)
  {
    boolean wasRemoved = false;
    if (purchaseOrders.contains(aPurchaseOrder))
    {
      purchaseOrders.remove(aPurchaseOrder);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPurchaseOrderAt(PurchaseOrder aPurchaseOrder, int index)
  {  
    boolean wasAdded = false;
    if(addPurchaseOrder(aPurchaseOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPurchaseOrders()) { index = numberOfPurchaseOrders() - 1; }
      purchaseOrders.remove(aPurchaseOrder);
      purchaseOrders.add(index, aPurchaseOrder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePurchaseOrderAt(PurchaseOrder aPurchaseOrder, int index)
  {
    boolean wasAdded = false;
    if(purchaseOrders.contains(aPurchaseOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPurchaseOrders()) { index = numberOfPurchaseOrders() - 1; }
      purchaseOrders.remove(aPurchaseOrder);
      purchaseOrders.add(index, aPurchaseOrder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPurchaseOrderAt(aPurchaseOrder, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfInWishlist()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addInWishlist(Game aInWishlist)
  {
    boolean wasAdded = false;
    if (inWishlist.contains(aInWishlist)) { return false; }
    inWishlist.add(aInWishlist);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeInWishlist(Game aInWishlist)
  {
    boolean wasRemoved = false;
    if (inWishlist.contains(aInWishlist))
    {
      inWishlist.remove(aInWishlist);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addInWishlistAt(Game aInWishlist, int index)
  {  
    boolean wasAdded = false;
    if(addInWishlist(aInWishlist))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfInWishlist()) { index = numberOfInWishlist() - 1; }
      inWishlist.remove(aInWishlist);
      inWishlist.add(index, aInWishlist);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveInWishlistAt(Game aInWishlist, int index)
  {
    boolean wasAdded = false;
    if(inWishlist.contains(aInWishlist))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfInWishlist()) { index = numberOfInWishlist() - 1; }
      inWishlist.remove(aInWishlist);
      inWishlist.add(index, aInWishlist);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addInWishlistAt(aInWishlist, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfInCart()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addInCart(Game aInCart)
  {
    boolean wasAdded = false;
    if (inCart.contains(aInCart)) { return false; }
    inCart.add(aInCart);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeInCart(Game aInCart)
  {
    boolean wasRemoved = false;
    if (inCart.contains(aInCart))
    {
      inCart.remove(aInCart);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addInCartAt(Game aInCart, int index)
  {  
    boolean wasAdded = false;
    if(addInCart(aInCart))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfInCart()) { index = numberOfInCart() - 1; }
      inCart.remove(aInCart);
      inCart.add(index, aInCart);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveInCartAt(Game aInCart, int index)
  {
    boolean wasAdded = false;
    if(inCart.contains(aInCart))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfInCart()) { index = numberOfInCart() - 1; }
      inCart.remove(aInCart);
      inCart.add(index, aInCart);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addInCartAt(aInCart, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    reviews.clear();
    purchaseOrders.clear();
    inWishlist.clear();
    inCart.clear();
    super.delete();
  }

}