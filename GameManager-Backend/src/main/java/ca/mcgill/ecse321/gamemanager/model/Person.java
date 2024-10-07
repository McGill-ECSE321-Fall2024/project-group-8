package ca.mcgill.ecse321.gamemanager.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/



// line 2 "model.ump"
// line 107 "model.ump"
public class Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  private String password;
  private String name;
  private String email;

  //Person Associations
  private PersonRole isARole;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person(String aPassword, String aName, String aEmail, PersonRole aIsARole)
  {
    password = aPassword;
    name = aName;
    email = aEmail;
    if (!setIsARole(aIsARole))
    {
      throw new RuntimeException("Unable to create Person due to aIsARole. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public String getPassword()
  {
    return password;
  }

  public String getName()
  {
    return name;
  }

  public String getEmail()
  {
    return email;
  }
  /* Code from template association_GetOne */
  public PersonRole getIsARole()
  {
    return isARole;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setIsARole(PersonRole aNewIsARole)
  {
    boolean wasSet = false;
    if (aNewIsARole != null)
    {
      isARole = aNewIsARole;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    isARole = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "password" + ":" + getPassword()+ "," +
            "name" + ":" + getName()+ "," +
            "email" + ":" + getEmail()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "isARole = "+(getIsARole()!=null?Integer.toHexString(System.identityHashCode(getIsARole())):"null");
  }
}