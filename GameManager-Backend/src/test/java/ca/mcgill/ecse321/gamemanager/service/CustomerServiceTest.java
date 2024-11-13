package ca.mcgill.ecse321.gamemanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamemanager.model.Customer;
import ca.mcgill.ecse321.gamemanager.repository.CustomerRepository;


@SpringBootTest
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    @AfterEach
    public void deleteDb(){
        customerRepository.deleteAll();
        ;
    }


    private static final String invalid_password_empty=" ";
    private static final String invalid_password_wrong="123 ";

    @Test
    public void testCreateValidCustomer() {
        String name = "Michael";
        String email = "michael1234@gmail.com";
        String password = "Michael123";

        Customer customer = new Customer(password,name, email);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerRepository.findCustomerByEmail(any(String.class))).thenReturn(null);


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
        Customer customer1 = new Customer(password1,name1, email1);
        Customer customer2 = new Customer(password2,name2, email2);

        when(customerRepository.findCustomerByEmail(any(String.class))).thenReturn(customer2);



        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(password2,name2, email2));
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
        Customer customer = new Customer(password,name, email);

        when(customerRepository.findCustomerByEmail(any(String.class))).thenReturn(customer);


        String newPassword = "Michael123";
        String newName = "Michael";
        Customer updatedCustomer = new Customer(newPassword,newName, email);
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        assertNotNull(updatedCustomer);
        assertEquals(newPassword, updatedCustomer.getPassword());
        assertEquals(newName, updatedCustomer.getName());

        //verify(customerRepository, times(1)).findCustomerByEmail(newEmail);
    }
    @Test
    public void testUpdateCustomerWithoutName(){
        String name = "Michael";
        String email = "michael@gmail.com";
        String password = "Michael123";
        Customer customer = new Customer(password,name, email);
        when(customerRepository.findCustomerByEmail(any(String.class))).thenReturn(customer);



        String newName = "";

        Customer updatedCustomer = new Customer(password,newName, email);
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()-> customerService.updateCustomer(email,newName, password) ) ;
        assertEquals("Failed to update customer with invalid name.",ex.getMessage());

    }
    @Test
    public void testUpdateCustomerWithNullName(){
        String name = "Michael";
        String email = "michael@gmail.com";
        String password = "Michael123";
        Customer customer = new Customer(password,name, email);
        when(customerRepository.findCustomerByEmail(any(String.class))).thenReturn(customer);


        String newName = null;

        Customer updatedCustomer = new Customer(password,newName, email);
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()-> customerService.updateCustomer(email,newName, password) ) ;
        assertEquals("Failed to update customer with invalid name.",ex.getMessage());


    }



    @Test
    public void testUpdateCustomerNullPassword(){
        String name = "Michael";
        String email = "michael@gmail.com";
        String password = "Michael123";
        Customer customer = new Customer(password,name, email);
        when(customerRepository.findCustomerByEmail(any(String.class))).thenReturn(customer);


        String newPassword = null;


       // Customer updatedCustomer = new Customer(newPassword,name, email);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()-> customerService.updateCustomer(email,name, newPassword) ) ;
        assertEquals("Failed to update customer with invalid password.",ex.getMessage());

    }
    @Test
    public void testUpdateCustomerInvalidPassword(){
        String name = "Michael";
        String email = "michael@gmail.com";
        String password = "Michael123";
        Customer customer = new Customer(name, email, password);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);


        String newPassword = "123";


        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()-> customerService.updateCustomer(email,name, newPassword) ) ;
        assertEquals("Failed to update customer with invalid password.",ex.getMessage());

    }

    @Test
    public void testDeleteCustomerWithValidEmail() {
        String name = "Michael";
        String email = "michael@gmail.com";
        String password = "Michael123";
        Customer customer = new Customer(password,name, email);

        when(customerRepository.findCustomerByEmail(any(String.class))).thenReturn(customer);

        customerService.deleteCustomer(email);

        verify(customerRepository, times(1)).delete(customer);
    }


}
