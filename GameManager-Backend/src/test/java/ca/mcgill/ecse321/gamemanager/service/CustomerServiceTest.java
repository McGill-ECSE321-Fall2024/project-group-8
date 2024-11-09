package ca.mcgill.ecse321.gamemanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamemanager.dto.CustomerDto;
import ca.mcgill.ecse321.gamemanager.dto.CustomerRequestDto;
import ca.mcgill.ecse321.gamemanager.model.Category;
import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.model.Customer;
import ca.mcgill.ecse321.gamemanager.repository.CustomerRepository;


@SpringBootTest
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testCreateCustomer() {
        String name = "Michael";
        String email = "michael@gmail.com";
        String password = "Michael";

        Customer customer = new Customer(name, email, password);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        //Act
        Customer createdCustomer = customerService.createCustomer(name, email, password);

        //Assert
        assertNotNull(createdCustomer);
        assertEquals(name, createdCustomer.getName());
        assertEquals(email, createdCustomer.getEmail());
        assertEquals(password, createdCustomer.getPassword());
        verify(customerRepository, times(1)).save(any(Customer.class));

    }
    @Test
    public void testUpdateCustomerEmail() {
        String name = "Michael";
        String email = "michael@gmail.com";

        String password = "Michael";
        Customer customer = new Customer(name, email, password);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        String newEmail = "newMichael@gmail.com";

        Customer updatedCustomer = new Customer(name, newEmail, password);

        assertNotNull(updatedCustomer);
        assertEquals(name, updatedCustomer.getName());
        assertEquals(email, updatedCustomer.getEmail());
        assertEquals(password, updatedCustomer.getPassword());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void testDeleteCustomer() {
        String name = "Michael";
        String email = "michael@gmail.com";
        String password = "Michael";
        Customer customer = new Customer(name, email, password);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        customerService.deleteCustomer(email);

        verify(customerRepository, times(1)).deleteById(email);
    }


}
