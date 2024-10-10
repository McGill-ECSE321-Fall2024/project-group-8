package ca.mcgill.ecse321.gamemanager.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Game {

  //------------------------
  // ENUMERATIONS
  //------------------------
    public enum GameStatus { 
        Onsale, Available, Archived 
    }

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Game Attributes
    @Id
    @GeneratedValue
    private int gameId;
    private String title;
    private String description;
    private String genre;
    private double price;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Order> orders;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private int stock;
    private GameStatus gameStatus;

    //Game Associations
    @OneToOne(mappedBy = "game", cascade = CascadeType.ALL)
    private Request request;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Review> reviews;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Game(int gameId, String title, String description, String genre, double price, Category category, int stock, GameStatus gameStatus, Request request) {
        this.gameId = gameId;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.gameStatus = gameStatus;
        if (request == null || request.getGame() != null) {
            throw new RuntimeException("Unable to create Game due to aRequest.");
        }
        this.request = request;
    }

    public Game(int gameId, String title, String description, String genre, double price, Category category, int stock, GameStatus gameStatus, Request.RequestType requestType, Request.RequestStatus requestStatus, Employee employee) {
        this.gameId = gameId;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.gameStatus = gameStatus;
        this.request = new Request(requestType, requestStatus, this, employee);
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