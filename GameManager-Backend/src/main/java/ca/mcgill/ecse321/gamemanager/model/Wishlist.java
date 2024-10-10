/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/
package ca.mcgill.ecse321.gamemanager.model;

import java.util.*;

import jakarta.persistence.*;

// line 69 "model.ump"
// line 148 "model.ump"
@Entity
public class Wishlist
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Wishlist Attributes
  @Id
  private int wishlistId;

  //Wishlist Associations
  @ManyToMany
//  @JoinTable(
//          name = "wishlist_game",  // Name of the join table
//          joinColumns = @JoinColumn(name = "wishlist_id"),  // Foreign key for Wishlist in join table
//          inverseJoinColumns = @JoinColumn(name = "game_id")  // Foreign key for Game in join table
//  )
  private List<Game> games;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_email")
  private Customer customer;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  @SuppressWarnings("unused")
  protected Wishlist(){}

  public Wishlist(int aWishlistId, Customer aCustomer)
  {
    wishlistId = aWishlistId;
    games = new ArrayList<Game>();
    if (aCustomer == null || aCustomer.getWishlist() != null)
    {
      throw new RuntimeException("Unable to create Wishlist due to aCustomer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    customer = aCustomer;
  }

  public Wishlist(int aWishlistId, String aPasswordForCustomer, String aNameForCustomer, String aEmailForCustomer)
  {
    wishlistId = aWishlistId;
    games = new ArrayList<Game>();
    customer = new Customer(aPasswordForCustomer, aNameForCustomer, aEmailForCustomer, this);
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

  public int getWishlistId()
  {
    return wishlistId;
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
  public Customer getCustomer()
  {
    return customer;
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

  public void delete()
  {
    games.clear();
    Customer existingCustomer = customer;
    customer = null;
    if (existingCustomer != null)
    {
      existingCustomer.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "wishlistId" + ":" + getWishlistId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }
}


