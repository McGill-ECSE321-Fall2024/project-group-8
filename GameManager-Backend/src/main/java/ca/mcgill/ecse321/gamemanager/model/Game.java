/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/
package ca.mcgill.ecse321.gamemanager.model;


import jakarta.persistence.*;
import java.util.*;
// line 72 "model.ump"
// line 136 "model.ump"
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
  @ManyToOne
  @JoinColumn(name = "games", foreignKey = @ForeignKey(name = "CATEGORY_NAME_FK")) // Add this line to specify the foreign key column in the Game table
  private Category category;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  @SuppressWarnings("unused")
  public Game() {
  }
  public Game(String aTitle, String aDescription, String aGenre, double aPrice, int aStock, GameStatus aGameStatus, RequestStatus aRequestStatus, Category aCategory)
  {
    title = aTitle;
    description = aDescription;
    genre = aGenre;
    price = aPrice;
    stock = aStock;
    gameStatus = aGameStatus;
    requestStatus = aRequestStatus;
    if (!setCategory(aCategory))
    {
      throw new RuntimeException("Unable to create Game due to aCategory. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  /* Code from template association_GetOne */
  public Category getCategory()
  {
    return category;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCategory(Category aNewCategory)
  {
    boolean wasSet = false;
    if (aNewCategory != null)
    {
      category = aNewCategory;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    category = null;
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
            "  " + "category = "+(getCategory()!=null?Integer.toHexString(System.identityHashCode(getCategory())):"null");
  }
}