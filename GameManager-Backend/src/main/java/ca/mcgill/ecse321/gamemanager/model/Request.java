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
  private int gameID;

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

  public Request(int aRequestId, RequestType aRequestType, RequestStatus aRequestStatus, int gameId , Employee aEmployee)
  {
    requestId = aRequestId;
    requestType = aRequestType;
    requestStatus = aRequestStatus;
    gameID = gameId;

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
  public int getGameId()
  {

    return gameID;
  }
  /* Code from template association_GetOne */
  public Employee getEmployee()
  {
    return employee;
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

  public void delete() {}


  public String toString()
  {
    Integer a = getGameId();
    return super.toString() + "["+
            "requestId" + ":" + getRequestId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "requestType" + "=" + (getRequestType() != null ? !getRequestType().equals(this)  ? getRequestType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "requestStatus" + "=" + (getRequestStatus() != null ? !getRequestStatus().equals(this)  ? getRequestStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "gameID = "+(a !=null?Integer.toHexString(System.identityHashCode(a)):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "employee = "+(getEmployee()!=null?Integer.toHexString(System.identityHashCode(getEmployee())):"null");
  }
}