package ca.mcgill.ecse321.gamemanager.repository;
import ca.mcgill.ecse321.gamemanager.model.Customer;
import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder;
import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static ca.mcgill.ecse321.gamemanager.model.PurchaseOrder.OrderStatus.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PurchaseOrderRepositoryTests {
  @Autowired
  private PurchaseOrderRepository repo;
   @Autowired
   private CustomerRepository customerRepository;

  @BeforeEach
  @AfterEach
  public void clearDatabase() {
      repo.deleteAll();
      customerRepository.deleteAll();
  }

  @Test
  public void testCreateAndReadOrder() {
      // create order
      Customer customer = new Customer("123", "Bob", "bob@gmail.com");
      customer = customerRepository.save(customer);

      OrderStatus status = Delivered;
      double price = 34.23;
      Date date = Date.valueOf("2024-10-10");

      PurchaseOrder test1 = new PurchaseOrder(status, price, date, customer);

      // save in database
      test1 = repo.save(test1);
      int test1Id = test1.getOrderId();

      // read back from database
      PurchaseOrder orderFromDb = repo.findByOrderId(test1Id);

      // assertions
      assertNotNull(orderFromDb);
      assertEquals(test1Id, orderFromDb.getOrderId());
      assertEquals(status, orderFromDb.getOrderStatus());
      assertEquals(price, orderFromDb.getTotalPrice());
      assertEquals(date, orderFromDb.getDate());
      assertEquals(customer, orderFromDb.getCustomer());
  }
}