package ca.mcgill.ecse321.gamemanager.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Owner extends PersonRole {

    //------------------------
    // ENUMERATIONS
    //------------------------

    public enum RequestType {
        Addition, Remove
    }

    public enum RequestStatus {
        Approved, Pending, Denied
    }

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    // Owner Associations

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Request> requests = new ArrayList<>();

    //------------------------
    // CONSTRUCTOR
    //------------------------

    // JPA requires a no-args constructor
    protected Owner() {
        super();
    }

    public Owner(int aId) {
        super(aId);
    }

    //------------------------
    // GETTERS AND SETTERS
    //------------------------

    public List<Category> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public boolean addCategory(Category aCategory) {
        if (categories.contains(aCategory)) {
            return false;
        }
        categories.add(aCategory);
        return true;
    }

    public boolean removeCategory(Category aCategory) {
        return categories.remove(aCategory);
    }

    public boolean addCategoryAt(Category aCategory, int index) {
        if (addCategory(aCategory)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfCategories()) {
                index = numberOfCategories() - 1;
            }
            categories.remove(aCategory);
            categories.add(index, aCategory);
            return true;
        }
        return false;
    }

    public boolean addOrMoveCategoryAt(Category aCategory, int index) {
        if (categories.contains(aCategory)) {
            categories.remove(aCategory);
            categories.add(index, aCategory);
            return true;
        } else {
            return addCategoryAt(aCategory, index);
        }
    }

    public int numberOfCategories() {
        return categories.size();
    }

    public boolean hasCategories() {
        return !categories.isEmpty();
    }

    public int indexOfCategory(Category aCategory) {
        return categories.indexOf(aCategory);
    }

    public List<Request> getRequests() {
        return Collections.unmodifiableList(requests);
    }

    public boolean addRequest(Request aRequest) {
        if (requests.contains(aRequest)) {
            return false;
        }
        requests.add(aRequest);
        return true;
    }

    public boolean removeRequest(Request aRequest) {
        return requests.remove(aRequest);
    }

    public boolean addRequestAt(Request aRequest, int index) {
        if (addRequest(aRequest)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfRequests()) {
                index = numberOfRequests() - 1;
            }
            requests.remove(aRequest);
            requests.add(index, aRequest);
            return true;
        }
        return false;
    }

    public boolean addOrMoveRequestAt(Request aRequest, int index) {
        if (requests.contains(aRequest)) {
            requests.remove(aRequest);
            requests.add(index, aRequest);
            return true;
        } else {
            return addRequestAt(aRequest, index);
        }
    }

    public int numberOfRequests() {
        return requests.size();
    }

    public boolean hasRequests() {
        return !requests.isEmpty();
    }

    public int indexOfRequest(Request aRequest) {
        return requests.indexOf(aRequest);
    }

    //------------------------
    // DELETE METHOD
    //------------------------

    @Override
    public void delete() {
        categories.clear();
        requests.clear();
        super.delete();
    }
}

// // line 31 "model.ump"
// // line 137 "model.ump"
// public class Owner extends PersonRole
// {

//   //------------------------
//   // ENUMERATIONS
//   //------------------------

//   public enum RequestType { Addition, Remove }
//   public enum RequestStatus { Approved, Pending, Denied }

//   //------------------------
//   // MEMBER VARIABLES
//   //------------------------

//   //Owner Associations
//   private List<Category> categories;
//   private List<Request> requests;

//   //------------------------
//   // CONSTRUCTOR
//   //------------------------

//   public Owner(int aId)
//   {
//     super(aId);
//     categories = new ArrayList<Category>();
//     requests = new ArrayList<Request>();
//   }

//   //------------------------
//   // INTERFACE
//   //------------------------
//   /* Code from template association_GetMany */
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
//   /* Code from template association_GetMany */
//   public Request getRequest(int index)
//   {
//     Request aRequest = requests.get(index);
//     return aRequest;
//   }

//   public List<Request> getRequests()
//   {
//     List<Request> newRequests = Collections.unmodifiableList(requests);
//     return newRequests;
//   }

//   public int numberOfRequests()
//   {
//     int number = requests.size();
//     return number;
//   }

//   public boolean hasRequests()
//   {
//     boolean has = requests.size() > 0;
//     return has;
//   }

//   public int indexOfRequest(Request aRequest)
//   {
//     int index = requests.indexOf(aRequest);
//     return index;
//   }
//   /* Code from template association_MinimumNumberOfMethod */
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
//   /* Code from template association_MinimumNumberOfMethod */
//   public static int minimumNumberOfRequests()
//   {
//     return 0;
//   }
//   /* Code from template association_AddUnidirectionalMany */
//   public boolean addRequest(Request aRequest)
//   {
//     boolean wasAdded = false;
//     if (requests.contains(aRequest)) { return false; }
//     requests.add(aRequest);
//     wasAdded = true;
//     return wasAdded;
//   }

//   public boolean removeRequest(Request aRequest)
//   {
//     boolean wasRemoved = false;
//     if (requests.contains(aRequest))
//     {
//       requests.remove(aRequest);
//       wasRemoved = true;
//     }
//     return wasRemoved;
//   }
//   /* Code from template association_AddIndexControlFunctions */
//   public boolean addRequestAt(Request aRequest, int index)
//   {  
//     boolean wasAdded = false;
//     if(addRequest(aRequest))
//     {
//       if(index < 0 ) { index = 0; }
//       if(index > numberOfRequests()) { index = numberOfRequests() - 1; }
//       requests.remove(aRequest);
//       requests.add(index, aRequest);
//       wasAdded = true;
//     }
//     return wasAdded;
//   }

//   public boolean addOrMoveRequestAt(Request aRequest, int index)
//   {
//     boolean wasAdded = false;
//     if(requests.contains(aRequest))
//     {
//       if(index < 0 ) { index = 0; }
//       if(index > numberOfRequests()) { index = numberOfRequests() - 1; }
//       requests.remove(aRequest);
//       requests.add(index, aRequest);
//       wasAdded = true;
//     } 
//     else 
//     {
//       wasAdded = addRequestAt(aRequest, index);
//     }
//     return wasAdded;
//   }

//   public void delete()
//   {
//     categories.clear();
//     requests.clear();
//     super.delete();
//   }

// }