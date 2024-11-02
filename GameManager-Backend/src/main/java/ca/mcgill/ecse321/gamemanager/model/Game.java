package ca.mcgill.ecse321.gamemanager.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import java.util.*;
import java.sql.Date;
import jakarta.persistence.*;

// line 71 "model.ump"
// line 141 "model.ump"
@Entity
public class Game
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum GameStatus { Onsale, Available, Archived }
  public enum RequestStatus { PendingApproval, Approved, PendingArchived, Archived }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int gameId;
  private String title;
  private String description;
  private String genre;
  private double price;
  private int stock;
  private GameStatus gameStatus;
  private RequestStatus requestStatus;

  //Game Associations
  private List<Review> reviews;
  private Owner owner;
  private Customer Wishlist;
  private Customer cart;
  private List<GameCopy> gameCopies;
  private Category category;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  @SuppressWarnings("unused")
  public Game() {
    gameCopies = new ArrayList<>();  // Initialize lists, game will have many copies
    reviews = new ArrayList<>();
  }


  public Game(String aTitle, String aDescription, String aGenre, double aPrice, int aStock, GameStatus aGameStatus, RequestStatus aRequestStatus, Owner aOwner, Category aCategory)
  {
    title = aTitle;
    description = aDescription;
    genre = aGenre;
    price = aPrice;
    stock = aStock;
    gameStatus = aGameStatus;
    requestStatus = aRequestStatus;
    reviews = new ArrayList<Review>();
    boolean didAddOwner = setOwner(aOwner);
    if (!didAddOwner)
    {
      throw new RuntimeException("Unable to create game due to owner. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    gameCopies = new ArrayList<GameCopy>();
    boolean didAddCategory = setCategory(aCategory);
    if (!didAddCategory)
    {
      throw new RuntimeException("Unable to create game due to category. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setGameId(int aGameId)
  {
    boolean wasSet = false;
    gameId = aGameId;
    wasSet = true;
    return wasSet;
  }

  public boolean setTitle(String aTitle)
  {
    boolean wasSet = false;
    title = aTitle;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setGenre(String aGenre)
  {
    boolean wasSet = false;
    genre = aGenre;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(double aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setStock(int aStock)
  {
    boolean wasSet = false;
    stock = aStock;
    wasSet = true;
    return wasSet;
  }

  public boolean setGameStatus(GameStatus aGameStatus)
  {
    boolean wasSet = false;
    gameStatus = aGameStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setRequestStatus(RequestStatus aRequestStatus)
  {
    boolean wasSet = false;
    requestStatus = aRequestStatus;
    wasSet = true;
    return wasSet;
  }

  public int getGameId()
  {
    return gameId;
  }

  public String getTitle()
  {
    return title;
  }

  public String getDescription()
  {
    return description;
  }

  public String getGenre()
  {
    return genre;
  }

  public double getPrice()
  {
    return price;
  }

  public int getStock()
  {
    return stock;
  }

  public GameStatus getGameStatus()
  {
    return gameStatus;
  }

  public RequestStatus getRequestStatus()
  {
    return requestStatus;
  }
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
  /* Code from template association_GetOne */
  public Owner getOwner()
  {
    return owner;
  }
  /* Code from template association_GetOne */
  public Customer getWishlist()
  {
    return Wishlist;
  }
  /* Code from template association_GetOne */
  public Customer getCart()
  {
    return cart;
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
  public Category getCategory()
  {
    return category;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReviews()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Review addReview(int aReviewId, int aRating, String aDescription, Date aDate, Customer aCreated)
  {
    return new Review(aReviewId, aRating, aDescription, aDate, aCreated, this);
  }

  public boolean addReview(Review aReview)
  {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) { return false; }
    Game existingGame = aReview.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame)
    {
      aReview.setGame(this);
    }
    else
    {
      reviews.add(aReview);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReview(Review aReview)
  {
    boolean wasRemoved = false;
    //Unable to remove aReview, as it must always have a game
    if (!this.equals(aReview.getGame()))
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
  /* Code from template association_SetOneToMany */
  public boolean setOwner(Owner aOwner)
  {
    boolean wasSet = false;
    if (aOwner == null)
    {
      return wasSet;
    }

    Owner existingOwner = owner;
    owner = aOwner;
    if (existingOwner != null && !existingOwner.equals(aOwner))
    {
      existingOwner.removeGame(this);
    }
    owner.addGame(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setWishlist(Customer aWishlist)
  {
    boolean wasSet = false;
    if (aWishlist == null)
    {
      return wasSet;
    }

    Customer existingWishlist = Wishlist;
    Wishlist = aWishlist;
    if (existingWishlist != null && !existingWishlist.equals(aWishlist))
    {
      existingWishlist.removeInWishlist(this);
    }
    Wishlist.addInWishlist(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCart(Customer aCart)
  {
    boolean wasSet = false;
    if (aCart == null)
    {
      return wasSet;
    }

    Customer existingCart = cart;
    cart = aCart;
    if (existingCart != null && !existingCart.equals(aCart))
    {
      existingCart.removeInCart(this);
    }
    cart.addInCart(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGameCopies()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public GameCopy addGameCopy(int aGameCopyId, PurchaseOrder aPurchaseOrder)
  {
    return new GameCopy(this);
  }

  public boolean addGameCopy(GameCopy aGameCopy)
  {
    boolean wasAdded = false;
    if (gameCopies.contains(aGameCopy)) { return false; }
    Game existingGame = aGameCopy.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame)
    {
      aGameCopy.setGame(this);
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
    //Unable to remove aGameCopy, as it must always have a game
    if (!this.equals(aGameCopy.getGame()))
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
  public boolean setCategory(Category aCategory)
  {
    boolean wasSet = false;
    if (aCategory == null)
    {
      return wasSet;
    }

    Category existingCategory = category;
    category = aCategory;
    if (existingCategory != null && !existingCategory.equals(aCategory))
    {
      existingCategory.removeGame(this);
    }
    category.addGame(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=reviews.size(); i > 0; i--)
    {
      Review aReview = reviews.get(i - 1);
      aReview.delete();
    }
    Owner placeholderOwner = owner;
    this.owner = null;
    if(placeholderOwner != null)
    {
      placeholderOwner.removeGame(this);
    }
    Customer placeholderWishlist = Wishlist;
    this.Wishlist = null;
    if(placeholderWishlist != null)
    {
      placeholderWishlist.removeInWishlist(this);
    }
    Customer placeholderCart = cart;
    this.cart = null;
    if(placeholderCart != null)
    {
      placeholderCart.removeInCart(this);
    }
    for(int i=gameCopies.size(); i > 0; i--)
    {
      GameCopy aGameCopy = gameCopies.get(i - 1);
      aGameCopy.delete();
    }
    Category placeholderCategory = category;
    this.category = null;
    if(placeholderCategory != null)
    {
      placeholderCategory.removeGame(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "gameId" + ":" + getGameId()+ "," +
            "title" + ":" + getTitle()+ "," +
            "description" + ":" + getDescription()+ "," +
            "genre" + ":" + getGenre()+ "," +
            "price" + ":" + getPrice()+ "," +
            "stock" + ":" + getStock()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "gameStatus" + "=" + (getGameStatus() != null ? !getGameStatus().equals(this)  ? getGameStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "requestStatus" + "=" + (getRequestStatus() != null ? !getRequestStatus().equals(this)  ? getRequestStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "owner = "+(getOwner()!=null?Integer.toHexString(System.identityHashCode(getOwner())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "Wishlist = "+(getWishlist()!=null?Integer.toHexString(System.identityHashCode(getWishlist())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "cart = "+(getCart()!=null?Integer.toHexString(System.identityHashCode(getCart())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "category = "+(getCategory()!=null?Integer.toHexString(System.identityHashCode(getCategory())):"null");
  }
}