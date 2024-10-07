package ca.mcgill.ecse321.gamemanager.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 25 "model.ump"
// line 130 "model.ump"
public class Employee extends PersonRole
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum RequestType { Addition, Remove }
  public enum RequestStatus { Approved, Pending, Denied }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Associations
  private List<Request> requests;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(int aId)
  {
    super(aId);
    requests = new ArrayList<Request>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Request getRequest(int index)
  {
    Request aRequest = requests.get(index);
    return aRequest;
  }

  public List<Request> getRequests()
  {
    List<Request> newRequests = Collections.unmodifiableList(requests);
    return newRequests;
  }

  public int numberOfRequests()
  {
    int number = requests.size();
    return number;
  }

  public boolean hasRequests()
  {
    boolean has = requests.size() > 0;
    return has;
  }

  public int indexOfRequest(Request aRequest)
  {
    int index = requests.indexOf(aRequest);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRequests()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Request addRequest(RequestType aRequestType, RequestStatus aRequestStatus, Game aGame)
  {
    return new Request(aRequestType, aRequestStatus, aGame, this);
  }

  public boolean addRequest(Request aRequest)
  {
    boolean wasAdded = false;
    if (requests.contains(aRequest)) { return false; }
    Employee existingEmployee = aRequest.getEmployee();
    boolean isNewEmployee = existingEmployee != null && !this.equals(existingEmployee);
    if (isNewEmployee)
    {
      aRequest.setEmployee(this);
    }
    else
    {
      requests.add(aRequest);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRequest(Request aRequest)
  {
    boolean wasRemoved = false;
    //Unable to remove aRequest, as it must always have a employee
    if (!this.equals(aRequest.getEmployee()))
    {
      requests.remove(aRequest);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addRequestAt(Request aRequest, int index)
  {  
    boolean wasAdded = false;
    if(addRequest(aRequest))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRequests()) { index = numberOfRequests() - 1; }
      requests.remove(aRequest);
      requests.add(index, aRequest);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRequestAt(Request aRequest, int index)
  {
    boolean wasAdded = false;
    if(requests.contains(aRequest))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRequests()) { index = numberOfRequests() - 1; }
      requests.remove(aRequest);
      requests.add(index, aRequest);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRequestAt(aRequest, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=requests.size(); i > 0; i--)
    {
      Request aRequest = requests.get(i - 1);
      aRequest.delete();
    }
    super.delete();
  }

}