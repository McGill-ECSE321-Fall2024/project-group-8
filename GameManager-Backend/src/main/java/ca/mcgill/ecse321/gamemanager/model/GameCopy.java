package ca.mcgill.ecse321.gamemanager.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

import jakarta.persistence.*;

// line 32 "model.ump"
// line 135 "model.ump"
@Entity
public class GameCopy
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GameCopy Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int gameCopyId;

  //GameCopy Associations
  @ManyToOne
  @JoinColumn(
          name = "game_id",
          foreignKey = @ForeignKey(name = "GAME_ID_FK")
  )
  private Game game;
  @ManyToOne
  @JoinColumn(
          name = "order_id",
          foreignKey = @ForeignKey(name = "ORDER_ID_FK")
  )
  private PurchaseOrder purchaseOrder;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  @SuppressWarnings("unused")
  public GameCopy() {}

  public GameCopy(Game aGame)
  {
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create gameCopy due to game. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setGameCopyId(int aGameCopyId)
  {
    boolean wasSet = false;
    gameCopyId = aGameCopyId;
    wasSet = true;
    return wasSet;
  }

  public int getGameCopyId()
  {
    return gameCopyId;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetOne */
  public PurchaseOrder getPurchaseOrder()
  {
    return purchaseOrder;
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
      existingGame.removeGameCopy(this);
    }
    game.addGameCopy(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setPurchaseOrder(PurchaseOrder aPurchaseOrder)
  {
    boolean wasSet = false;
    if (aPurchaseOrder == null)
    {
      return wasSet;
    }

    PurchaseOrder existingPurchaseOrder = purchaseOrder;
    purchaseOrder = aPurchaseOrder;
    if (existingPurchaseOrder != null && !existingPurchaseOrder.equals(aPurchaseOrder))
    {
      existingPurchaseOrder.removeGameCopy(this);
    }
    purchaseOrder.addGameCopy(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeGameCopy(this);
    }
    PurchaseOrder placeholderPurchaseOrder = purchaseOrder;
    this.purchaseOrder = null;
    if(placeholderPurchaseOrder != null)
    {
      placeholderPurchaseOrder.removeGameCopy(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "gameCopyId" + ":" + getGameCopyId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "purchaseOrder = "+(getPurchaseOrder()!=null?Integer.toHexString(System.identityHashCode(getPurchaseOrder())):"null");
  }
}