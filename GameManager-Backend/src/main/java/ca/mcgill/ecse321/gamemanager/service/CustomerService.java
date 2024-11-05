package ca.mcgill.ecse321.gamemanager.service;

import ca.mcgill.ecse321.gamemanager.model.Customer;
import ca.mcgill.ecse321.gamemanager.repository.CustomerRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    // Retrieve a customer by email (used as ID in this case)
    public Customer findCustomerByEmail(String email) {
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepo.findCustomerByEmail(email));
        return optionalCustomer.orElseThrow(() -> new IllegalArgumentException("Invalid Customer email."));
    }

    // Retrieve all customers
    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepo.findAll();
    }

    // Create a new customer
    @Transactional
    public Customer createCustomer(String name, String email, String password) {
        if (customerRepo.findCustomerByEmail(email) != null) {
            throw new IllegalArgumentException("A customer with this email already exists.");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        Customer newCustomer = new Customer(name, email, password);
        return customerRepo.save(newCustomer);
    }

    // Update an existing customer by email
    @Transactional
    public Customer updateCustomer(String email, String name, String newEmail, String password) {
        Customer customer = findCustomerByEmail(email);
        
        if (name != null && !name.isBlank()) {
            customer.setName(name);
        }
        if (newEmail != null && !newEmail.isBlank()) {
            if (!newEmail.equals(email) && customerRepo.findCustomerByEmail(newEmail) != null) {
                throw new IllegalArgumentException("New email is already in use by another customer.");
            }
            customer.setEmail(newEmail);
        }
        if (password != null && password.length() >= 8) {
            customer.setPassword(password);
        }

        return customerRepo.save(customer);
    }

    // Delete a customer by email
    @Transactional
    public void deleteCustomer(String email) {
        if (customerRepo.findCustomerByEmail(email) == null) {
            throw new IllegalArgumentException("Customer with email " + email + " does not exist.");
        }
        customerRepo.deleteById(email);
    }
}
