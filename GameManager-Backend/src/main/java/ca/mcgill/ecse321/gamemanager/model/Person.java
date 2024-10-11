/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/
package ca.mcgill.ecse321.gamemanager.model;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

// line 2 "model.ump"
// line 90 "model.ump"
@MappedSuperclass
public class Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  private String password;
  private String name;
  @Id
  @Column(unique = true, nullable = false)
  private String email;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  @SuppressWarnings("unused")
  protected Person(){}

  public Person(String aPassword, String aName, String aEmail)
  {
    password = aPassword;
    name = aName;
    email = aEmail;
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

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "password" + ":" + getPassword()+ "," +
            "name" + ":" + getName()+ "," +
            "email" + ":" + getEmail()+ "]";
  }
}