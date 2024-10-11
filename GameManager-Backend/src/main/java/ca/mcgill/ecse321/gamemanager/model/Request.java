/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/
package ca.mcgill.ecse321.gamemanager.model;

import jakarta.persistence.*;

// line 78 "model.ump"
// line 153 "model.ump"
@Entity
public class Request
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum RequestType { Addition, Remove }
  public enum RequestStatus { Approved, Pending, Denied }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Request Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int requestId;
  private RequestType requestType;
  private RequestStatus requestStatus;

  //Request Associations
  @ManyToOne
  @JoinColumn(
        name = "game_id",
        foreignKey = @ForeignKey(name = "GAME_ID_FK")
  )
  private Game game;
  @ManyToOne
  @JoinColumn(
          name = "employee_email",
          foreignKey = @ForeignKey(name = "EMPLOYEE_EMAIL_FK")
  )
  private Employee employee;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  @SuppressWarnings("unused")
  protected Request(){}

  public Request(int aRequestId, RequestType aRequestType, RequestStatus aRequestStatus, Game aGame, Employee aEmployee)
  {
    requestId = aRequestId;
    requestType = aRequestType;
    requestStatus = aRequestStatus;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create request due to game. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddEmployee = setEmployee(aEmployee);
    if (!didAddEmployee)
    {
      throw new RuntimeException("Unable to create request due to employee. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRequestId(int aRequestId)
  {
    boolean wasSet = false;
    requestId = aRequestId;
    wasSet = true;
    return wasSet;
  }

  public boolean setRequestType(RequestType aRequestType)
  {
    boolean wasSet = false;
    requestType = aRequestType;
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

  public int getRequestId()
  {
    return requestId;
  }

  public RequestType getRequestType()
  {
    return requestType;
  }

  public RequestStatus getRequestStatus()
  {
    return requestStatus;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetOne */
  public Employee getEmployee()
  {
    return employee;
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
      existingGame.removeRequest(this);
    }
    game.addRequest(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setEmployee(Employee aEmployee)
  {
    boolean wasSet = false;
    if (aEmployee == null)
    {
      return wasSet;
    }

    Employee existingEmployee = employee;
    employee = aEmployee;
    if (existingEmployee != null && !existingEmployee.equals(aEmployee))
    {
      existingEmployee.removeRequest(this);
    }
    employee.addRequest(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeRequest(this);
    }
    Employee placeholderEmployee = employee;
    this.employee = null;
    if(placeholderEmployee != null)
    {
      placeholderEmployee.removeRequest(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "requestId" + ":" + getRequestId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "requestType" + "=" + (getRequestType() != null ? !getRequestType().equals(this)  ? getRequestType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "requestStatus" + "=" + (getRequestStatus() != null ? !getRequestStatus().equals(this)  ? getRequestStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "employee = "+(getEmployee()!=null?Integer.toHexString(System.identityHashCode(getEmployee())):"null");
  }
}


