package ca.mcgill.ecse321.gamemanager.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;

public abstract class PersonRole {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    @Id
    @GeneratedValue
    private int id;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    // JPA requires a no-argument constructor
    protected PersonRole() {
    }

    // Constructor for setting the ID manually
    public PersonRole(int aId) {
        this.id = aId;
    }

    //------------------------
    // GETTERS AND SETTERS
    //------------------------

    public int getId() {
        return id;
    }

    public boolean setId(int aId) {
        this.id = aId;
        return true;
    }

    //------------------------
    // DELETE METHOD
    //------------------------

    public void delete() {
    }
}

//   //------------------------
//   // MEMBER VARIABLES
//   //------------------------

//   //PersonRole Attributes
//   private int id;

//   //------------------------
//   // CONSTRUCTOR
//   //------------------------

//   public PersonRole(int aId)
//   {
//     id = aId;
//   }

//   //------------------------
//   // INTERFACE
//   //------------------------

//   public boolean setId(int aId)
//   {
//     boolean wasSet = false;
//     id = aId;
//     wasSet = true;
//     return wasSet;
//   }

//   public int getId()
//   {
//     return id;
//   }

//   public void delete()
//   {}


//   public String toString()
//   {
//     return super.toString() + "["+
//             "id" + ":" + getId()+ "]";
//   }
// }