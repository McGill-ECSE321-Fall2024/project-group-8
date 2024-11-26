/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/
package ca.mcgill.ecse321.gamemanager.model;

import java.util.*;
import jakarta.persistence.*;

// line 26 "model.ump"
// line 118 "model.ump"
@Entity
public class Owner extends Person {

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum GameStatus {Onsale, Available, Archived}

  public enum RequestStatus {PendingApproval, Approved, PendingArchived, Archived}

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------
  @SuppressWarnings("unused")
  public Owner() {
  }

  public Owner(String aPassword, String aName, String aEmail) {
    super(aPassword, aName, aEmail);
  }
  public void delete()
  {
    super.delete();
  }

}