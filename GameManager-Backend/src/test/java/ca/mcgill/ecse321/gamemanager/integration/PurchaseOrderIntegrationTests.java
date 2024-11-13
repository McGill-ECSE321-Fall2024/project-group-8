package ca.mcgill.ecse321.gamemanager.integration;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.gamemanager.dto.ErrorDto;
import ca.mcgill.ecse321.gamemanager.dto.PurchaseOrderDto;
import ca.mcgill.ecse321.gamemanager.dto.PurchaseOrderRequestDto;
import ca.mcgill.ecse321.gamemanager.model.GameCopy;
import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder.OrderStatus;
import ca.mcgill.ecse321.gamemanager.repository.GameCopyRepository;
import ca.mcgill.ecse321.gamemanager.repository.GameRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PurchaseOrderIntegrationTests {
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameCopyRepository gameCopyRepository;

    private int orderId;
    private final OrderStatus VALID_STATUS = OrderStatus.ShoppingCart;
    private final OrderStatus INVALID_STATUS = null;
    private final double VALID_PRICE = 123.99;
    private final double INVALID_PRICE = -2;
    private final Date VALID_DATE = Date.valueOf(LocalDate.now());
    private final Date INVALID_DATE = null;


    public PurchaseOrderIntegrationTests() {
    }

    @Test
    @Order(1)
    public void testCreateValidOrder() {
        // Arrange
        PurchaseOrderRequestDto request = new PurchaseOrderRequestDto(VALID_STATUS, VALID_PRICE);

        // Act
        ResponseEntity<PurchaseOrderDto> response = client.postForEntity("/api/orders", request, PurchaseOrderDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        PurchaseOrderDto createdOrder = response.getBody();
        assertNotNull(createdOrder);
        assertEquals(VALID_PRICE, createdOrder.getPrice());
        assertEquals(VALID_STATUS, createdOrder.getOrderStatus());
        assertNotNull(createdOrder.getOrderId());
        this.orderId = createdOrder.getOrderId();
    }

    @Test
    @Order(2)
    public void testCreateInvalidOrder() {
        // Arrange
        PurchaseOrderRequestDto request = new PurchaseOrderRequestDto(VALID_STATUS, INVALID_PRICE);

        // Act
        ResponseEntity<ErrorDto> response = client.postForEntity("/api/orders", request, ErrorDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorDto body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getErrors().size());
        assertEquals("Price cannot be negative.", body.getErrors().get(0));
    }

    @Test
    @Order(3)
    public void testFindOrderById() {
        // Arrange
        String url = "/api/orders/" + this.orderId;

        // Act
        ResponseEntity<PurchaseOrderDto> response = client.getForEntity(url, PurchaseOrderDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PurchaseOrderDto createdOrder = response.getBody();
        assertNotNull(createdOrder);
        assertEquals(VALID_PRICE, createdOrder.getPrice());
        assertEquals(VALID_STATUS, createdOrder.getOrderStatus());
        assertNotNull(createdOrder.getOrderId());
        this.orderId = createdOrder.getOrderId();
    }

    @Test
    @Order(4)
    public void testUpdateValidOrder() {
        // Arrange
        String url = "/api/orders/" + this.orderId;
        PurchaseOrderDto updated = new PurchaseOrderDto(orderId, OrderStatus.ShoppingCart, 1, VALID_DATE);

        client.put(url, updated);

        // Act
        ResponseEntity<PurchaseOrderDto> response = client.getForEntity(url, PurchaseOrderDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PurchaseOrderDto updatedOrder = response.getBody();
        assertNotNull(updatedOrder);
        assertEquals(1, updatedOrder.getPrice());
        assertEquals(OrderStatus.ShoppingCart, updatedOrder.getOrderStatus());
    }

    @Test
    @Order(5)
    public void testAddGameToCart() {
        GameCopy gameCopy1 = gameCopyRepository.save(new GameCopy());
        int game1 = gameCopy1.getGameCopyId();

        GameCopy gameCopy2 = gameCopyRepository.save(new GameCopy());
        int game2 = gameCopy2.getGameCopyId();

        List<Integer> VALID_GAMES = List.of(game1, game2);

        String url = "/api/orders/" + this.orderId + "/cart";
        client.put(url, VALID_GAMES);

        String orderUrl = "/api/orders/" + this.orderId;

        ResponseEntity<PurchaseOrderDto> response = client.getForEntity(orderUrl, PurchaseOrderDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PurchaseOrderDto updatedOrder = response.getBody();
        assertNotNull(updatedOrder);

        List<GameCopy> gameCopies = new ArrayList<>();
        for (int id : VALID_GAMES) {
            GameCopy gameCopy = gameCopyRepository.findGameCopyByGameCopyId(id);
            gameCopies.add(gameCopy);
        }

        assertEquals(updatedOrder.getGameCopies(), gameCopies);
    }

    @Test
    @Order(6)
    public void testCheckOut() {
        String url = "/api/orders/" + this.orderId;
        PurchaseOrderDto request = new PurchaseOrderDto(orderId, VALID_STATUS, VALID_PRICE, VALID_DATE);
        client.postForEntity(url, request, PurchaseOrderDto.class);

        ResponseEntity<PurchaseOrderDto> response = client.getForEntity(url, PurchaseOrderDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PurchaseOrderDto updatedOrder = response.getBody();
        assertNotNull(updatedOrder);
        assertEquals(OrderStatus.Bought, updatedOrder.getOrderStatus());
    }

    @Test
    @Order(7)
    public void testDeleteOrder() {
        String url = "/api/orders/" + this.orderId;
        client.delete(url);

        ResponseEntity<String> response = client.getForEntity(url, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // should already be deleted
    }
}
