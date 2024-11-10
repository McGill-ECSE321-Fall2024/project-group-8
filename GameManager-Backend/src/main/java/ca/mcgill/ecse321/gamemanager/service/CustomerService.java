package ca.mcgill.ecse321.gamemanager.service;

import ca.mcgill.ecse321.gamemanager.model.Customer;
import ca.mcgill.ecse321.gamemanager.repository.CustomerRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepo;

    @Transactional
    public Customer createCustomer(String name, String email, String password) {

        if (customerRepo.findCustomerByEmail(email) != null) {
            throw new IllegalArgumentException("A customer with this email already exists.");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }

        Customer newCustomer = new Customer(password,name, email);
        return customerRepo.save(newCustomer);
    }



    // Retrieve a customer by email (used as ID in this case)
    public Customer findCustomerByEmail(String email) {

        Customer customer = customerRepo.findCustomerByEmail(email);

        if(customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Customer with email %s not found", email));
        }

        return customer;
    }

    // Retrieve all customers
    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepo.findAll();
    }

    // Create a new customer


    // Update an existing customer by email
    @Transactional
    public Customer updateCustomer(String email, String name, String newEmail, String password) {

        Customer customer = customerRepo.findCustomerByEmail(email);

        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Customer with email %s not found", email));
        }
        if (name != null && !name.isBlank()) {
            customer.setName(name);
        }
        else{
            throw new IllegalArgumentException("Failed to update customer with invalid name.");

        }
        if (newEmail != null && !newEmail.isBlank()) {
            if (!email.equals(newEmail) && customerRepo.findCustomerByEmail(newEmail) != null) {
                throw new IllegalArgumentException("New email is already in use by another customer.");
            }
            customer.setEmail(newEmail);
        }
        else{
            throw new IllegalArgumentException("Failed to update customer with invalid email.");

        }
        if (password != null && password.length() >= 8) {
            customer.setPassword(password);
        }
        else{
            throw new IllegalArgumentException("Failed to update customer with invalid password.");
        }

        return customerRepo.save(customer);
    }

    // Delete a customer by email
    @Transactional
    public void deleteCustomer(String email) {

        Customer customer = findCustomerByEmail(email);

        if (customer == null) {
            throw new IllegalArgumentException("Customer with email " + email + " does not exist.");
        }
        customerRepo.delete(customer);
    }
}
