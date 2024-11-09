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

import javax.swing.text.StyledEditorKit;


@SpringBootTest
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private static final String invalid_password_empty=" ";
    private static final String invalid_password_wrong="123 ";

    @Test
    public void testCreateValidCustomer() {
        String name = "Michael";
        String email = "michael@gmail.com";
        String password = "Michael123";

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
    public void testCreateInvalidCustomerWithExistedEmail() {
        String name1 = "Michael";
        String email1 = "michael@gmail.com";
        String password1 = "Michael123";

        String name2 = "Ang";
        String email2 = "michael@gmail.com";
        String password2 = "Ang12345";
        Customer customer1 = new Customer(name1, email1, password1);
        Customer customer2 = new Customer(name2, email2, password2);


        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(name2,email2,password2));
        assertEquals("A customer with this email already exists.",e.getMessage());
    }

    @Test
    public void testCreateInvalidCustomerWithInvalidPassword() {
        String name = "Michael";
        String email = "michael@gmail.com";
        IllegalArgumentException invalidPassword = assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(name,email,invalid_password_wrong));
        assertEquals("Password must be at least 8 characters long.",invalidPassword.getMessage());
    }
    @Test
    public void testCreateInvalidCustomerWithEmptyPassword(){
        String name = "Michael";
        String email = "michael@gmail.com";
        IllegalArgumentException emptyPassword = assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(name,email,null));
        assertEquals("Password must be at least 8 characters long.",emptyPassword.getMessage());
    }

    @Test
    public void testUpdateCustomerValid() {
        String name = "Michael";
        String email = "michael@gmail.com";

        String password = "Michael123";
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
    public void testUpdateCustomerWithoutName(){
        String name = "Michael";
        String email = "michael@gmail.com";
        String password = "Michael123";
        Customer customer = new Customer(name, email, password);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        String newEmail = "newMichael@gmail.com";
        String newName = "";

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()-> customerService.updateCustomer(email,newName, newEmail, password) ) ;
        assertEquals("Failed to update customer with invalid name.",ex.getMessage());

    }
    @Test
    public void testUpdateCustomerWithNullName(){
        String name = "Michael";
        String email = "michael@gmail.com";
        String password = "Michael123";
        Customer customer = new Customer(name, email, password);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        String newEmail = "newMichael@gmail.com";
        String newName = null;

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()-> customerService.updateCustomer(email,newName, newEmail, password) ) ;
        assertEquals("Failed to update customer with invalid name.",ex.getMessage());


    }
    @Test
    public void testUpdateCustomerInvalidEmail(){
        String name = "Michael";
        String email = "michael@gmail.com";
        String password = "Michael123";
        Customer customer = new Customer(name, email, password);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        String newEmail = "";


        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()-> customerService.updateCustomer(email,name, newEmail, password) ) ;
        assertEquals("Failed to update customer with invalid email.",ex.getMessage());


    }
    @Test
    public void testUpdateCustomerNullEmail(){
        String name = "Michael";
        String email = "michael@gmail.com";
        String password = "Michael123";
        Customer customer = new Customer(name, email, password);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        String newEmail = null;


        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()-> customerService.updateCustomer(email,name, newEmail, password) ) ;
        assertEquals("Failed to update customer with invalid email.",ex.getMessage());

    }
    @Test
    public void testUpdateCustomerUsedEmail(){
        String name = "Michael";
        String email = "michael@gmail.com";
        String password = "Michael123";
        Customer customer = new Customer(name, email, password);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        String newEmail = email;


        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()-> customerService.updateCustomer(email,name, newEmail, password) ) ;
        assertEquals("New email is already in use by another customer.",ex.getMessage());

    }
    @Test
    public void testUpdateCustomerNullPassword(){
        String name = "Michael";
        String email = "michael@gmail.com";
        String password = "Michael123";
        Customer customer = new Customer(name, email, password);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        String newEmail = "newMichael@gmail.com";
        String newPassword = null;


        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()-> customerService.updateCustomer(email,name, newEmail, newPassword) ) ;
        assertEquals("Failed to update customer with invalid password.",ex.getMessage());

    }
    @Test
    public void testUpdateCustomerInvalidPassword(){
        String name = "Michael";
        String email = "michael@gmail.com";
        String password = "Michael123";
        Customer customer = new Customer(name, email, password);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        String newEmail = "newMichael@gmail.com";
        String newPassword = "123";


        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()-> customerService.updateCustomer(email,name, newEmail, newPassword) ) ;
        assertEquals("Failed to update customer with invalid password.",ex.getMessage());

    }

    @Test
    public void testDeleteCustomer() {
        String name = "Michael";
        String email = "michael@gmail.com";
        String password = "Michael123";
        Customer customer = new Customer(name, email, password);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        customerService.deleteCustomer(email);

        verify(customerRepository, times(1)).deleteById(email);
    }


}
