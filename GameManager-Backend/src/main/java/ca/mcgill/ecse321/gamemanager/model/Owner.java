/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/
package ca.mcgill.ecse321.gamemanager.model;

import java.util.*;
import jakarta.persistence.*;

// line 23 "model.ump"
// line 116 "model.ump"
@Entity
public class Owner extends Person
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum RequestType { Addition, Remove }
  public enum RequestStatus { Approved, Pending, Denied }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Associations
//   @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//   private List<Category> categories;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Request> handledRequests;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  @SuppressWarnings("unused")
  protected Owner(){}

  public Owner(String aPassword, String aName, String aEmail)
  {
    super(aPassword, aName, aEmail);
    // categories = new ArrayList<Category>();
    handledRequests = new ArrayList<Request>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
//   public Category getCategory(int index)
//   {
//     Category aCategory = categories.get(index);
//     return aCategory;
//   }

//   public List<Category> getCategories()
//   {
//     List<Category> newCategories = Collections.unmodifiableList(categories);
//     return newCategories;
//   }

//   public int numberOfCategories()
//   {
//     int number = categories.size();
//     return number;
//   }

//   public boolean hasCategories()
//   {
//     boolean has = categories.size() > 0;
//     return has;
//   }

//   public int indexOfCategory(Category aCategory)
//   {
//     int index = categories.indexOf(aCategory);
//     return index;
//   }
  /* Code from template association_GetMany */
  public Request getHandledRequest(int index)
  {
    Request aHandledRequest = handledRequests.get(index);
    return aHandledRequest;
  }

  public List<Request> getHandledRequests()
  {
    List<Request> newHandledRequests = Collections.unmodifiableList(handledRequests);
    return newHandledRequests;
  }

  public int numberOfHandledRequests()
  {
    int number = handledRequests.size();
    return number;
  }

  public boolean hasHandledRequests()
  {
    boolean has = handledRequests.size() > 0;
    return has;
  }

  public int indexOfHandledRequest(Request aHandledRequest)
  {
    int index = handledRequests.indexOf(aHandledRequest);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
//   public static int minimumNumberOfCategories()
//   {
//     return 0;
//   }
//   /* Code from template association_AddUnidirectionalMany */
//   public boolean addCategory(Category aCategory)
//   {
//     boolean wasAdded = false;
//     if (categories.contains(aCategory)) { return false; }
//     categories.add(aCategory);
//     wasAdded = true;
//     return wasAdded;
//   }

//   public boolean removeCategory(Category aCategory)
//   {
//     boolean wasRemoved = false;
//     if (categories.contains(aCategory))
//     {
//       categories.remove(aCategory);
//       wasRemoved = true;
//     }
//     return wasRemoved;
//   }
//   /* Code from template association_AddIndexControlFunctions */
//   public boolean addCategoryAt(Category aCategory, int index)
//   {
//     boolean wasAdded = false;
//     if(addCategory(aCategory))
//     {
//       if(index < 0 ) { index = 0; }
//       if(index > numberOfCategories()) { index = numberOfCategories() - 1; }
//       categories.remove(aCategory);
//       categories.add(index, aCategory);
//       wasAdded = true;
//     }
//     return wasAdded;
//   }

//   public boolean addOrMoveCategoryAt(Category aCategory, int index)
//   {
//     boolean wasAdded = false;
//     if(categories.contains(aCategory))
//     {
//       if(index < 0 ) { index = 0; }
//       if(index > numberOfCategories()) { index = numberOfCategories() - 1; }
//       categories.remove(aCategory);
//       categories.add(index, aCategory);
//       wasAdded = true;
//     }
//     else
//     {
//       wasAdded = addCategoryAt(aCategory, index);
//     }
//     return wasAdded;
//   }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfHandledRequests()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addHandledRequest(Request aHandledRequest)
  {
    boolean wasAdded = false;
    if (handledRequests.contains(aHandledRequest)) { return false; }
    handledRequests.add(aHandledRequest);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeHandledRequest(Request aHandledRequest)
  {
    boolean wasRemoved = false;
    if (handledRequests.contains(aHandledRequest))
    {
      handledRequests.remove(aHandledRequest);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addHandledRequestAt(Request aHandledRequest, int index)
  {
    boolean wasAdded = false;
    if(addHandledRequest(aHandledRequest))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHandledRequests()) { index = numberOfHandledRequests() - 1; }
      handledRequests.remove(aHandledRequest);
      handledRequests.add(index, aHandledRequest);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveHandledRequestAt(Request aHandledRequest, int index)
  {
    boolean wasAdded = false;
    if(handledRequests.contains(aHandledRequest))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHandledRequests()) { index = numberOfHandledRequests() - 1; }
      handledRequests.remove(aHandledRequest);
      handledRequests.add(index, aHandledRequest);
      wasAdded = true;
    }
    else
    {
      wasAdded = addHandledRequestAt(aHandledRequest, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    // categories.clear();
    handledRequests.clear();
    super.delete();
  }

}


