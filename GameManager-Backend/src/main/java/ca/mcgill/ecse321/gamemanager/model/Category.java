/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/
package ca.mcgill.ecse321.gamemanager.model;

import jakarta.persistence.*;
import java.util.*;

// line 39 "model.ump"
// line 149 "model.ump"
@Entity
public class Category
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Category Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int categoryId;
  private String name;
  private String description;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  @SuppressWarnings("unused")
  public Category(){}
  public Category(String aName, String aDescription)
  {

    name = aName;
    description = aDescription;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCategoryId(int aCategoryId)
  {
    boolean wasSet = false;
    categoryId = aCategoryId;
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

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public int getCategoryId()
  {
    return categoryId;
  }

  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return description;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "categoryId" + ":" + getCategoryId()+ "," +
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "]";
  }
}