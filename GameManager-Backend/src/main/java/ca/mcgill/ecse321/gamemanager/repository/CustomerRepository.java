package ca.mcgill.ecse321.gamemanager.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gamemanager.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String> {
    public Customer findCustomerByEmail(String email);
}
