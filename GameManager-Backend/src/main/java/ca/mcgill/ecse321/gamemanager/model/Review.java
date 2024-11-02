package ca.mcgill.ecse321.gamemanager.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import java.sql.Date;
import jakarta.persistence.*;
// line 48 "model.ump"
// line 122 "model.ump"
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id", "game_id"}))
public class Review
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Review Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int reviewId;
  private int rating;
  private String description;
  private Date date;

  //Review Associations
  @ManyToOne
  @JoinColumn(
          name = "Customer_id",
          foreignKey = @ForeignKey(name = "CUSTOMER_ID_FK")
  )
  private Customer created;
  @ManyToOne
  @JoinColumn(
          name = "game_id",
          foreignKey = @ForeignKey(name = "GAME_ID_FK")
  )
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Review(int aReviewId, int aRating, String aDescription, Date aDate, Customer aCreated, Game aGame)
  {
    reviewId = aReviewId;
    rating = aRating;
    description = aDescription;
    date = aDate;
    boolean didAddCreated = setCreated(aCreated);
    if (!didAddCreated)
    {
      throw new RuntimeException("Unable to create review due to created. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create review due to game. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setReviewId(int aReviewId)
  {
    boolean wasSet = false;
    reviewId = aReviewId;
    wasSet = true;
    return wasSet;
  }

  public boolean setRating(int aRating)
  {
    boolean wasSet = false;
    rating = aRating;
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

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public int getReviewId()
  {
    return reviewId;
  }

  public int getRating()
  {
    return rating;
  }

  public String getDescription()
  {
    return description;
  }

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetOne */
  public Customer getCreated()
  {
    return created;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
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
      existingCreated.removeReview(this);
    }
    created.addReview(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame == null)
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removeReview(this);
    }
    game.addReview(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Customer placeholderCreated = created;
    this.created = null;
    if(placeholderCreated != null)
    {
      placeholderCreated.removeReview(this);
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeReview(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "reviewId" + ":" + getReviewId()+ "," +
            "rating" + ":" + getRating()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "created = "+(getCreated()!=null?Integer.toHexString(System.identityHashCode(getCreated())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}