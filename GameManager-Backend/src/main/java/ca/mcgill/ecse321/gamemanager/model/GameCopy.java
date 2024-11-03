package ca.mcgill.ecse321.gamemanager.model;

import jakarta.persistence.*;

// line 54 "model.ump"
// line 166 "model.ump"
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

  @ManyToOne
  @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "CUSTOMER_EMAIL_FK"))
  private Customer buyer;

  @ManyToOne
  @JoinColumn(name = "game_id", foreignKey = @ForeignKey(name = "GAME_ID_FK"))
  private Game game;

  @ManyToOne
  @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "ORDER_ID_FK"))
  private PurchaseOrder purchaseOrder;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GameCopy() {}

  //------------------------
  // INTERFACE
  //------------------------

  public int getGameCopyId()
  {
    return gameCopyId;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "gameCopyId" + ":" + getGameCopyId()+ "]";
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------  
}

