package ca.mcgill.ecse321.gamemanager.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;


@Entity // Marks this class as a JPA entity
public class Person {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    @Id
    @GeneratedValue
    private Long id;

    // Person Attributes
    private String password;
    private String name;
    private String email;

    // Person Associations
    @OneToOne
    private PersonRole isARole;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    // JPA requires a no-args constructor
    protected Person() {
    }

    public Person(String aPassword, String aName, String aEmail, PersonRole aIsARole) {
        this.password = aPassword;
        this.name = aName;
        this.email = aEmail;
        if (!setIsARole(aIsARole)) {
            throw new RuntimeException("Unable to create Person due to aIsARole.");
        }
    }

    //------------------------
    // GETTERS AND SETTERS
    //------------------------

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public boolean setPassword(String aPassword) {
        this.password = aPassword;
        return true;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String aName) {
        this.name = aName;
        return true;
    }

    public String getEmail() {
        return email;
    }

    public boolean setEmail(String aEmail) {
        this.email = aEmail;
        return true;
    }

    // Getter for the PersonRole association
    public PersonRole getIsARole() {
        return isARole;
    }

    // Setter for the PersonRole association
    public boolean setIsARole(PersonRole aNewIsARole) {
        if (aNewIsARole != null) {
            this.isARole = aNewIsARole;
            return true;
        }
        return false;
    }

    //------------------------
    // DELETE AND TOSTRING
    //------------------------

    public void delete() {
        this.isARole = null;
    }
}


// {
// 	//------------------------
// 	// MEMBER VARIABLES
// 	//------------------------

// 	public Person(String aPassword, String aName, String aEmail, PersonRole aIsARole)
// 	{
// 		password = aPassword;
// 		name = aName;
// 		email = aEmail;
// 		if (!setIsARole(aIsARole))
// 		{
// 			throw new RuntimeException("Unable to create Person due to aIsARole. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
// 		}
// 	}

// 	//------------------------
// 	// INTERFACE
// 	//------------------------

// 	public boolean setPassword(String aPassword)
// 	{
// 		boolean wasSet = false;
// 		password = aPassword;
// 		wasSet = true;
// 		return wasSet;
// 	}

// 	public boolean setName(String aName)
// 	{
// 		boolean wasSet = false;
// 		name = aName;
// 		wasSet = true;
// 		return wasSet;
// 	}

// 	public boolean setEmail(String aEmail)
// 	{
// 		boolean wasSet = false;
// 		email = aEmail;
// 		wasSet = true;
// 		return wasSet;
// 	}

// 	public String getPassword()
// 	{
// 		return password;
// 	}

// 	public String getName()
// 	{
// 		return name;
// 	}

// 	public String getEmail()
// 	{
// 		return email;
// 	}
// 	/* Code from template association_GetOne */
// 	public PersonRole getIsARole()
// 	{
// 		return isARole;
// 	}
// 	/* Code from template association_SetUnidirectionalOne */
// 	public boolean setIsARole(PersonRole aNewIsARole)
// 	{
// 		boolean wasSet = false;
// 		if (aNewIsARole != null)
// 		{
// 			isARole = aNewIsARole;
// 			wasSet = true;
// 		}
// 		return wasSet;
// 	}

// 	public void delete()
// 	{
// 		isARole = null;
// 	}


// 	public String toString()
// 	{
// 		return super.toString() + "["+
// 						"password" + ":" + getPassword()+ "," +
// 						"name" + ":" + getName()+ "," +
// 						"email" + ":" + getEmail()+ "]" + System.getProperties().getProperty("line.separator") +
// 						"  " + "isARole = "+(getIsARole()!=null?Integer.toHexString(System.identityHashCode(getIsARole())):"null");
// 	}
// }