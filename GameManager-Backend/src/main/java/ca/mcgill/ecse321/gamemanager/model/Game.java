/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/
package ca.mcgill.ecse321.gamemanager.model;

import jakarta.persistence.*;

import java.util.*;
import java.sql.Date;

// line 32 "model.ump"
// line 127 "model.ump"
@Entity
public class Game
{
//  public void setCategory(Category category) {
//    this.category = category;
//  }

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum GameStatus { OnSale, InStock, OutOfStock, Archived }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int gameId; //let system automatically generate a gameID
  private String title;
  private String description;
  private String genre;
  private double price;
  private int stock;
  private GameStatus gameStatus;

  //Game Associations
  @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<GameCopy> gameCopies;

  @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews;

  @ManyToOne
  @JoinColumn(name = "games", foreignKey = @ForeignKey(name = "CATEGORY_NAME_FK")) // Add this line to specify the foreign key column in the Game table
  private Category category;

  @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Request> requests;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  @SuppressWarnings("unused")
  public Game() {
    gameCopies = new ArrayList<>();  // Initialize lists, game will have many copies
    reviews = new ArrayList<>();
    requests = new ArrayList<>();
  }

  public Game(String aTitle, String aDescription, String aGenre, double aPrice, int aStock, Category aCategory, GameStatus aGameStatus)
  {
    title = aTitle;
    description = aDescription;
    genre = aGenre;
    price = aPrice;
    stock = aStock;
    category = aCategory;
    gameStatus = aGameStatus;
    gameCopies = new ArrayList<GameCopy>();
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

  public boolean setGenre(String aGenre){
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

//  public int getStock()
//  {
//    return gameCopies.size();
//  }

  public int getStock()
  {
    return stock;
  }

  public GameStatus getGameStatus()
  {
    return gameStatus;
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

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGameCopies()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addGameCopy(GameCopy aGameCopy)
  {
    boolean wasAdded = false;
    if (gameCopies.contains(aGameCopy)) { return false; }
    gameCopies.add(aGameCopy);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGameCopy(GameCopy aGameCopy)
  {
    boolean wasRemoved = false;
    if (gameCopies.contains(aGameCopy))
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
    if(gameCopies.contains(aGameCopy)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfGameCopies()) {
        index = numberOfGameCopies() - 1;
      }
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

  public Review addReview(int aRating, String aDescription, Date aDate, Customer aReviewer)
  {
    return new Review(aRating, aDescription, aDate, this, aReviewer);
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
  public void delete()
  {
    for(int i=reviews.size(); i > 0; i--)
    {
      Review aReview = reviews.get(i - 1);
      aReview.delete();
    }
    while (requests.size() > 0)
    {
      Request aRequest = requests.get(requests.size() - 1);
      aRequest.delete();
      requests.remove(aRequest);
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
            "  " + "gameStatus" + "=" + (getGameStatus() != null ? !getGameStatus().equals(this)  ? getGameStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator");
  }
}
