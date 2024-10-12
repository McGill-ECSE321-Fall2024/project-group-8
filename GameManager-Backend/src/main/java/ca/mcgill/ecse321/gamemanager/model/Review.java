/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/
package ca.mcgill.ecse321.gamemanager.model;

import jakarta.persistence.*;

import java.sql.Date;

// line 49 "model.ump"
// line 138 "model.ump"
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id", "game_id"}))
// to ensure one customer can only have one review for each purchased game
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
          name = "game_id",
          foreignKey = @ForeignKey(name = "GAME_ID_FK")
  )
  public Game game;

  @ManyToOne
  @JoinColumn(
        name = "customer_id",
        foreignKey = @ForeignKey(name = "CUSTOMER_EMAIL_FK")
  )
  private Customer reviewer;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  @SuppressWarnings("unused")
  public Review(){}

  public Review(int aRating, String aDescription, Date aDate, Game aGame, Customer aReviewer)
  {
    //reviewId = aReviewId;
    rating = aRating;
    description = aDescription;
    date = aDate;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create review due to game. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddReviewer = setReviewer(aReviewer);
    if (!didAddReviewer)
    {
      throw new RuntimeException("Unable to create review due to reviewer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetOnes */
  public Customer getReviewer()
  {
    return reviewer;
  }

  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    game = aGame;
    wasSet = true;
    return wasSet;
  }

  /* Code from template association_SetOneToMany */
  public boolean setReviewer(Customer aReviewer)
  {
    boolean wasSet = false;
    if (aReviewer == null)
    {
      return wasSet;
    }

    Customer existingReviewer = reviewer;
    reviewer = aReviewer;
    if (existingReviewer != null && !existingReviewer.equals(aReviewer))
    {
      existingReviewer.removeReview(this);
    }
    reviewer.addReview(this);
    wasSet = true;
    return wasSet;
  }

  public void delete() {}

  public String toString()
  {
    return super.toString() + "["+
            "reviewId" + ":" + getReviewId()+ "," +
            "rating" + ":" + getRating()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "reviewer = "+(getReviewer()!=null?Integer.toHexString(System.identityHashCode(getReviewer())):"null");
  }
}


