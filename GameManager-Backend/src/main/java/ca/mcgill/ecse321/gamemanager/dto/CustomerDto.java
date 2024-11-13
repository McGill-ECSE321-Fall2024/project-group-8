package ca.mcgill.ecse321.gamemanager.dto;
import ca.mcgill.ecse321.gamemanager.model.Customer;

public class CustomerDto {
    private String name;
    private String email;
    private String password;


    public CustomerDto() {}

    public CustomerDto(Customer customer) {
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.password = customer.getPassword() ;
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

}
