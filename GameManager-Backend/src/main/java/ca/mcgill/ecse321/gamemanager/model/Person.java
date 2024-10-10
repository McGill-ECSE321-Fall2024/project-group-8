package ca.mcgill.ecse321.gamemanager.model;

import jakarta.persistence.*;

@Entity // Marks this class as a JPA entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    @Id
    @GeneratedValue
    private Long id;

    private String password;
    private String name;
    private String email;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    // JPA requires a no-args constructor
    protected Person() {}

    public Person(String password, String name, String email) {
        this.password = password;
        this.name = name;
        this.email = email;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //------------------------
    // DELETE METHOD
    //------------------------

    public void delete() {
        // Any specific cleanup needed for Person
    }
}