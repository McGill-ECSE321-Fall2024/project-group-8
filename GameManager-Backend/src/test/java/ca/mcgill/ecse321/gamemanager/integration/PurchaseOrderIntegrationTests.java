package ca.mcgill.ecse321.gamemanager.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.time.LocalDate;

import ca.mcgill.ecse321.gamemanager.dto.PurchaseOrderDto;
import ca.mcgill.ecse321.gamemanager.dto.PurchaseOrderRequestDto;
import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder.OrderStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PurchaseOrderIntegrationTests {
    @Autowired
    private TestRestTemplate client;

    private int orderId;
    private final OrderStatus VALID_STATUS = OrderStatus.Bought;
    private final OrderStatus INVALID_STATUS = null;
    private final double VALID_PRICE = 123.99;
    private final double INVALID_PRICE = -2;
    private final Date VALID_DATE = Date.valueOf(LocalDate.now());
    private final Date INVALID_DATE = null;

//    @Test
//    @Order(1)
//    public void testCreateValidOrder() {
//        // Arrange
//        PurchaseOrderRequestDto request = new PurchaseOrderRequestDto(VALID_STATUS, VALID_PRICE);
//
//        // Act
//        ResponseEntity<PurchaseOrderDto> response = client.postForEntity("/api/orders", request, PurchaseOrderDto.class);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        PurchaseOrderDto createdOrder = response.getBody();
//        assertNotNull(createdOrder);
//        assertEquals(VALID_PRICE, createdOrder.getPrice());
//        assertEquals(VALID_STATUS, createdOrder.getOrderStatus());
//        assertEquals(Date.valueOf(LocalDate.now()), createdOrder.getDate());
//        assertNotNull(createdOrder.getOrderId());
//        this.orderId = createdOrder.getOrderId();
//    }

    /*
    @Test
    @Order(2)
    public void testReadPersonByValidId() {
        // Arrange
        String url = "/people/" + this.validId;

        // Act
        ResponseEntity<PersonResponseDto> response = client.getForEntity(url, PersonResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PersonResponseDto person = response.getBody();
        assertNotNull(person);
        assertEquals(VALID_NAME, person.getName());
        assertEquals(VALID_EMAIL, person.getEmail());
        assertEquals(this.validId, person.getId());
        assertEquals(LocalDate.now(), person.getCreationDate());
    }
     */

    // getAllOrders
    // findOrderById
    // createOrder
    // updateOrder
    // deleteOrder
}
