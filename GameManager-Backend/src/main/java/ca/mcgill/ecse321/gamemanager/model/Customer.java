package ca.mcgill.ecse321.gamemanager.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.sql.Date;

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

    // Customer Associations
    @OneToOne
    private Wishlist wishlist;

    @OneToMany
    private List<Review> reviews;

    @OneToMany
    private List<Order> orders;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    // Hibernate requires a no-args constructor
    protected Customer() {
        super();
    }

    public Customer(String password, String name, String email, PersonRole personRole, Wishlist wishlist) {
        super(password, name, email, personRole);
        this.wishlist = wishlist;
    }

    //------------------------
    // INTERFACE
    //------------------------

    // Getters and Setters

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public List<Review> getReviews() {
        return Collections.unmodifiableList(reviews);
    }

    public boolean addReview(Review review) {
        return reviews.add(review);
    }

    public boolean removeReview(Review review) {
        return reviews.remove(review);
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    public boolean addOrder(Order order) {
        return orders.add(order);
    }

    public boolean removeOrder(Order order) {
        return orders.remove(order);
    }

    @Override
    public void delete() {
        if (wishlist != null) {
            wishlist.delete();
        }
        reviews.clear();
        orders.clear();
        super.delete();
    }
}

// {

//   //------------------------
//   // ENUMERATIONS
//   //------------------------

//   public enum OrderStatus { ShoppingCart, Bought }

//   //------------------------
//   // MEMBER VARIABLES
//   //------------------------

//   //Customer Associations
//   private Wishlist wishlist;
//   private List<Review> reviews;
//   private List<Order> orders;

//   //------------------------
//   // CONSTRUCTOR
//   //------------------------

//   public Customer(int aId, Wishlist aWishlist)
//   {
//     super(aId);
//     if (aWishlist == null || aWishlist.getOwns() != null)
//     {
//       throw new RuntimeException("Unable to create Customer due to aWishlist. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
//     }
//     wishlist = aWishlist;
//     reviews = new ArrayList<Review>();
//     orders = new ArrayList<Order>();
//   }

//   public Customer(int aId, int aWishlistIdForWishlist, Customer aCustomerForWishlist)
//   {
//     super(aId);
//     wishlist = new Wishlist(aWishlistIdForWishlist, aCustomerForWishlist, this);
//     reviews = new ArrayList<Review>();
//     orders = new ArrayList<Order>();
//   }

//   //------------------------
//   // INTERFACE
//   //------------------------
//   /* Code from template association_GetOne */
//   public Wishlist getWishlist()
//   {
//     return wishlist;
//   }
//   /* Code from template association_GetMany */
//   public Review getReview(int index)
//   {
//     Review aReview = reviews.get(index);
//     return aReview;
//   }

//   public List<Review> getReviews()
//   {
//     List<Review> newReviews = Collections.unmodifiableList(reviews);
//     return newReviews;
//   }

//   public int numberOfReviews()
//   {
//     int number = reviews.size();
//     return number;
//   }

//   public boolean hasReviews()
//   {
//     boolean has = reviews.size() > 0;
//     return has;
//   }

//   public int indexOfReview(Review aReview)
//   {
//     int index = reviews.indexOf(aReview);
//     return index;
//   }
//   /* Code from template association_GetMany */
//   public Order getOrder(int index)
//   {
//     Order aOrder = orders.get(index);
//     return aOrder;
//   }

//   public List<Order> getOrders()
//   {
//     List<Order> newOrders = Collections.unmodifiableList(orders);
//     return newOrders;
//   }

//   public int numberOfOrders()
//   {
//     int number = orders.size();
//     return number;
//   }

//   public boolean hasOrders()
//   {
//     boolean has = orders.size() > 0;
//     return has;
//   }

//   public int indexOfOrder(Order aOrder)
//   {
//     int index = orders.indexOf(aOrder);
//     return index;
//   }
//   /* Code from template association_MinimumNumberOfMethod */
//   public static int minimumNumberOfReviews()
//   {
//     return 0;
//   }
//   /* Code from template association_AddUnidirectionalMany */
//   public boolean addReview(Review aReview)
//   {
//     boolean wasAdded = false;
//     if (reviews.contains(aReview)) { return false; }
//     reviews.add(aReview);
//     wasAdded = true;
//     return wasAdded;
//   }

//   public boolean removeReview(Review aReview)
//   {
//     boolean wasRemoved = false;
//     if (reviews.contains(aReview))
//     {
//       reviews.remove(aReview);
//       wasRemoved = true;
//     }
//     return wasRemoved;
//   }
//   /* Code from template association_AddIndexControlFunctions */
//   public boolean addReviewAt(Review aReview, int index)
//   {  
//     boolean wasAdded = false;
//     if(addReview(aReview))
//     {
//       if(index < 0 ) { index = 0; }
//       if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
//       reviews.remove(aReview);
//       reviews.add(index, aReview);
//       wasAdded = true;
//     }
//     return wasAdded;
//   }

//   public boolean addOrMoveReviewAt(Review aReview, int index)
//   {
//     boolean wasAdded = false;
//     if(reviews.contains(aReview))
//     {
//       if(index < 0 ) { index = 0; }
//       if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
//       reviews.remove(aReview);
//       reviews.add(index, aReview);
//       wasAdded = true;
//     } 
//     else 
//     {
//       wasAdded = addReviewAt(aReview, index);
//     }
//     return wasAdded;
//   }
//   /* Code from template association_MinimumNumberOfMethod */
//   public static int minimumNumberOfOrders()
//   {
//     return 0;
//   }
//   /* Code from template association_AddUnidirectionalMany */
//   public boolean addOrder(Order aOrder)
//   {
//     boolean wasAdded = false;
//     if (orders.contains(aOrder)) { return false; }
//     orders.add(aOrder);
//     wasAdded = true;
//     return wasAdded;
//   }

//   public boolean removeOrder(Order aOrder)
//   {
//     boolean wasRemoved = false;
//     if (orders.contains(aOrder))
//     {
//       orders.remove(aOrder);
//       wasRemoved = true;
//     }
//     return wasRemoved;
//   }
//   /* Code from template association_AddIndexControlFunctions */
//   public boolean addOrderAt(Order aOrder, int index)
//   {  
//     boolean wasAdded = false;
//     if(addOrder(aOrder))
//     {
//       if(index < 0 ) { index = 0; }
//       if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
//       orders.remove(aOrder);
//       orders.add(index, aOrder);
//       wasAdded = true;
//     }
//     return wasAdded;
//   }

//   public boolean addOrMoveOrderAt(Order aOrder, int index)
//   {
//     boolean wasAdded = false;
//     if(orders.contains(aOrder))
//     {
//       if(index < 0 ) { index = 0; }
//       if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
//       orders.remove(aOrder);
//       orders.add(index, aOrder);
//       wasAdded = true;
//     } 
//     else 
//     {
//       wasAdded = addOrderAt(aOrder, index);
//     }
//     return wasAdded;
//   }

//   public void delete()
//   {
//     Wishlist existingWishlist = wishlist;
//     wishlist = null;
//     if (existingWishlist != null)
//     {
//       existingWishlist.delete();
//     }
//     reviews.clear();
//     orders.clear();
//     super.delete();
//   }

// }