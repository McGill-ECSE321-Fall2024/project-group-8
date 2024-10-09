package ca.mcgill.ecse321.gamemanager.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import jakarta.persistence.*;

// line 40 "model.ump"
// line 162 "model.ump"
@Entity
public class Game
{

  //------------------------
  // ENUMERATIONS
  //------------------------
  public enum GameStatus { Onsale, Available, Archived }


  @Id

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  @GeneratedValue
  private int gameId;
  private String title;
  private String description;
  private String genre;
  private double price;
  private Category category;
  private int stock;
  private GameStatus gameStatus;

  //Game Associations
  @EmbeddedId //composite primary key
  @OneToOne

  private Request request;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(int aGameId, String aTitle, String aDescription, String aGenre, double aPrice, Category aCategory, int aStock, GameStatus aGameStatus, Request aRequest)
  {
    gameId = aGameId;
    title = aTitle;
    description = aDescription;
    genre = aGenre;
    price = aPrice;
    category =  aCategory;
    stock = aStock;
    gameStatus = aGameStatus;
    if (aRequest == null || aRequest.getGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aRequest. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    request = aRequest;
  }

  public Game(int aGameId, String aTitle, String aDescription, String aGenre, double aPrice, Category aCategory, int aStock, GameStatus aGameStatus, RequestType aRequestTypeForRequest, RequestStatus aRequestStatusForRequest, Employee aEmployeeForRequest)
  {
    gameId = aGameId;
    title = aTitle;
    description = aDescription;
    genre = aGenre;
    price = aPrice;
    category = aCategory;
    stock = aStock;
    gameStatus = aGameStatus;
    request = new Request(aRequestTypeForRequest, aRequestStatusForRequest, this, aEmployeeForRequest);
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

  public boolean setCategory(Category aCategory)
  {
    boolean wasSet = false;
    Category category = aCategory;
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

  public Category getCategory()
  {
    return category;
  }

  public int getStock()
  {
    return stock;
  }

  public GameStatus getGameStatus()
  {
    return gameStatus;
  }
  /* Code from template association_GetOne */
  public Request getRequest()
  {
    return request;
  }

  public void delete()
  {
    Request existingRequest = request;
    request = null;
    if (existingRequest != null)
    {
      existingRequest.delete();
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
            "  " + "category" + "=" + (getCategory() != null ? !getCategory().equals(this)  ? getCategory().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "gameStatus" + "=" + (getGameStatus() != null ? !getGameStatus().equals(this)  ? getGameStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "request = "+(getRequest()!=null?Integer.toHexString(System.identityHashCode(getRequest())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 48 "model.ump"
 //game <-> * Category categories ;

  
}