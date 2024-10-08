package ca.mcgill.ecse321.gamemanager.model;/*PLEASE DO NOT EDIT THIS CODE*/
import ca.mcgill.ecse321.gamemanager.model.Game.GameStatus;


// line 95 "model.ump"
// line 172 "model.ump"
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
  private RequestType requestType;
  private RequestStatus requestStatus;

  //Request Associations
  private Game game;
  private Employee employee;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Request(RequestType aRequestType, RequestStatus aRequestStatus, Game aGame, Employee aEmployee)
  {
    requestType = aRequestType;
    requestStatus = aRequestStatus;
    if (aGame == null || aGame.getRequest() != null)
    {
      throw new RuntimeException("Unable to create Request due to aGame. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    game = aGame;
    boolean didAddEmployee = setEmployee(aEmployee);
    if (!didAddEmployee)
    {
      throw new RuntimeException("Unable to create request due to employee. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Request(RequestType aRequestType, RequestStatus aRequestStatus, int aGameIdForGame, String aTitleForGame, String aDescriptionForGame, String aGenreForGame, double aPriceForGame, Category aCategoryForGame, int aStockForGame, GameStatus aGameStatusForGame, Employee aEmployee)
  {
    requestType = aRequestType;
    requestStatus = aRequestStatus;
    game = new Game(aGameIdForGame, aTitleForGame, aDescriptionForGame, aGenreForGame, aPriceForGame, aCategoryForGame, aStockForGame, aGameStatusForGame, this);
    boolean didAddEmployee = setEmployee(aEmployee);
    if (!didAddEmployee)
    {
      throw new RuntimeException("Unable to create request due to employee. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

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
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
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
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "requestType" + "=" + (getRequestType() != null ? !getRequestType().equals(this)  ? getRequestType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "requestStatus" + "=" + (getRequestStatus() != null ? !getRequestStatus().equals(this)  ? getRequestStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "employee = "+(getEmployee()!=null?Integer.toHexString(System.identityHashCode(getEmployee())):"null");
  }
}