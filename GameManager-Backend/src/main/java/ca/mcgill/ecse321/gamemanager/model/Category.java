package ca.mcgill.ecse321.gamemanager.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/



// line 53 "model.ump"
// line 156 "model.ump"
public class Category
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Category Attributes
  private int categoryId;
  private String name;
  private String description;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Category(int aCategoryId, String aName, String aDescription)
  {
    categoryId = aCategoryId;
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
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------


  
}