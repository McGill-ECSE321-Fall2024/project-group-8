package ca.mcgill.ecse321.gamemanager.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Owner extends Person {

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Category> categories;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Request> requests;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    // JPA requires a no-args constructor
    protected Owner() {
        super();
    }

    public Owner(String password, String name, String email) {
        super(password, name, email);
    }

    //------------------------
    // GETTERS AND SETTERS
    //------------------------

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
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
