package ca.mcgill.ecse321.gamemanager.model;

import jakarta.persistence.*;
import java.util.List;

import ca.mcgill.ecse321.gamemanager.model.Request.RequestStatus;
import ca.mcgill.ecse321.gamemanager.model.Request.RequestType;

@Entity
public class Employee extends Person {

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Request> requests;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    // JPA requires a no-args constructor
    protected Employee() {
        super();
    }

    public Employee(String password, String name, String email) {
        super(password, name, email);
    }

    //------------------------
    // GETTERS AND SETTERS
    //------------------------

    public List<Request> getRequests() {
        return requests;
    }

    public Request addRequest(RequestType requestType, RequestStatus requestStatus, Game game) {
        Request newRequest = new Request(requestType, requestStatus, game, this);
        this.requests.add(newRequest);
        return newRequest;
    }

    public boolean addRequest(Request request) {
        if (requests.contains(request)) {
            return false;
        }
        requests.add(request);
        return true;
    }

    public boolean removeRequest(Request request) {
        if (requests.contains(request)) {
            requests.remove(request);
            return true;
        }
        return false;
    }

    //------------------------
    // DELETE METHOD
    //------------------------

    @Override
    public void delete() {
        requests.clear();
        super.delete();
    }
}
