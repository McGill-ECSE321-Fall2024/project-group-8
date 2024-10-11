package ca.mcgill.ecse321.gamemanager.repository;


import ca.mcgill.ecse321.gamemanager.model.Customer;
import ca.mcgill.ecse321.gamemanager.model.Order;
import ca.mcgill.ecse321.gamemanager.model.Order.OrderStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

@SpringBootTest
public class OrderRepositoryTests {

    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private OrderRepository orderRepo;


    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        orderRepo.deleteAll();
        customerRepo.deleteAll();
    }


/*
    @Test
    public void testCreateAndReadOrder() {
        int orderId = 347;
        double totalPrice = 99.0;
        Date date = Date.valueOf("2024-02-09");
        OrderStatus OS= OrderStatus.valueOf("ShoppingCart");

        Customer testCustomer = new Customer("password", "testName", "Name");

        Order testOrder = new Order(orderId, totalPrice, date, OS,buyer);
    }
*/
}
