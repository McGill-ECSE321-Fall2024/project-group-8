package ca.mcgill.ecse321.gamemanager.service;

import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder;
import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder.OrderStatus;
import org.junit.jupiter.api.Test;

import ca.mcgill.ecse321.gamemanager.repository.PurchaseOrderRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PurchaseOrderServiceTests {
    @Mock
    private PurchaseOrderRepository repo;

    @InjectMocks
    private PurchaseOrderService service;

    @SuppressWarnings("null")
    @Test
    public void testCreateValidOrder() {
        // Arrange
        OrderStatus status = OrderStatus.Bought;
        double totalPrice = 53.2;
        Date date = Date.valueOf(LocalDate.now());

        PurchaseOrder order = new PurchaseOrder(status, totalPrice, date);
        when(repo.save(any(PurchaseOrder.class))).thenReturn(order);

        // Act
        PurchaseOrder createdOrder = service.createOrder(status, totalPrice);

        // Assert
        assertNotNull(createdOrder);
        assertEquals(status, createdOrder.getOrderStatus());
        assertEquals(totalPrice, createdOrder.getTotalPrice());
        assertEquals(date, createdOrder.getDate());
        verify(repo, times(1)).save(any(PurchaseOrder.class));
    }

    @Test
    public void testCreateInvalidOrderPrice() {
        // Arrange
        OrderStatus status = OrderStatus.Bought;
        double totalPrice = -3;

        // Act and assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.createOrder(status, totalPrice));
        assertEquals("Price cannot be negative.", exception.getMessage());
    }

    @Test
    public void testCreateInvalidOrderStatus() {
        // Arrange
        OrderStatus status = null;
        double totalPrice = 50;

        // Act and assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.createOrder(status, totalPrice));
        assertEquals("Status cannot be null.", exception.getMessage());
    }

    @Test
    public void testGetAllOrders() {
        // Arrange
        PurchaseOrder order1 =
                new PurchaseOrder(OrderStatus.Bought, 1234.0, Date.valueOf(LocalDate.now()));
        order1.setOrderId(0);

        PurchaseOrder order2 =
                new PurchaseOrder(OrderStatus.ShoppingCart, 0.99, Date.valueOf(LocalDate.now()));
        order2.setOrderId(1);

        // grouping together to check equality later
        List<PurchaseOrder> orders = Arrays.asList(order1, order2);
         when(repo.findAll()).thenReturn(orders);

         // Act
        List<PurchaseOrder> result = service.getAllOrders();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testUpdateOrder() {
        // Arrange
        int id = 0;
        OrderStatus oldStatus = OrderStatus.Bought;
        double oldPrice = 1234;
        Date oldDate = Date.valueOf(LocalDate.now());

        PurchaseOrder order = new PurchaseOrder(oldStatus, oldPrice, oldDate);
        order.setOrderId(id);
        when(repo.findByOrderId(id)).thenReturn(order);

        // new info for updating
        OrderStatus newStatus = OrderStatus.ShoppingCart;
        double newPrice = 4321.99;
        Date newDate = Date.valueOf(LocalDate.now());

        // Act
        PurchaseOrder result = service.updateOrder(id, newStatus, newPrice);
        when(repo.findByOrderId(id)).thenReturn(result);

        // Assert
        assertNotNull(result);
        assertEquals(newPrice, result.getTotalPrice());
        assertEquals(newStatus, result.getOrderStatus());
        assertEquals(newDate, result.getDate());
        verify(repo, times(1)).findByOrderId(id);
    }

    @Test
    public void testUpdateInvalidOrderPrice() {
        // Arrange
        int id = 0;
        OrderStatus oldStatus = OrderStatus.Bought;
        double oldPrice = 1234;
        Date oldDate = Date.valueOf(LocalDate.now());

        PurchaseOrder order = new PurchaseOrder(oldStatus, oldPrice, oldDate);
        order.setOrderId(id);
        when(repo.findByOrderId(id)).thenReturn(order);

        // new info for updating
        OrderStatus newStatus = OrderStatus.ShoppingCart;
        double newPrice = -5;

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> service.updateOrder(id, newStatus, newPrice));
        assertEquals("Price cannot be negative.", exception.getMessage());
    }

    @Test
    public void testUpdateInvalidOrderStatus() {
        // Arrange
        int id = 0;
        OrderStatus oldStatus = OrderStatus.Bought;
        double oldPrice = 1234;
        Date oldDate = Date.valueOf(LocalDate.now());

        PurchaseOrder order = new PurchaseOrder(oldStatus, oldPrice, oldDate);
        order.setOrderId(id);
        when(repo.findByOrderId(id)).thenReturn(order);

        // new info for updating
        OrderStatus newStatus = null;
        double newPrice = 2;
        Date newDate = Date.valueOf(LocalDate.now());

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> service.updateOrder(id, newStatus, newPrice));
        assertEquals("Status cannot be null.", exception.getMessage());
    }

    @Test
    public void testFindOrderByValidId() {
        // Arrange
        int id = 0;
        PurchaseOrder order =
                new PurchaseOrder(OrderStatus.ShoppingCart, 23.45, Date.valueOf(LocalDate.now()));
        order.setOrderId(id);

        when(repo.findByOrderId(id)).thenReturn(order);

        // Act
        PurchaseOrder foundOrder = service.findOrderById(id);

        // Assert
        assertNotNull(foundOrder);
        assertEquals(OrderStatus.ShoppingCart, foundOrder.getOrderStatus());
        assertEquals(23.45, foundOrder.getTotalPrice());
        assertEquals(Date.valueOf(LocalDate.now()), foundOrder.getDate());
        verify(repo, times(1)).findByOrderId(id);
    }

    @Test
    public void testFindOrderByInvalidId() {
        // Arrange
        int invalidId = 1;
        when(repo.findByOrderId(invalidId)).thenReturn(null);

        // Act and assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.findOrderById(invalidId));
        assertEquals("There is no order with ID " + invalidId + ".", exception.getMessage());
    }

    @Test
    public void testDeleteOrderByValidId() {
        // Arrange
        int id = 1;
        PurchaseOrder order =
                new PurchaseOrder(OrderStatus.ShoppingCart, 23.45, Date.valueOf(LocalDate.now()));
        order.setOrderId(id);

        when(repo.findByOrderId(id)).thenReturn(order);

        // Act
        service.deleteOrder(id);

        // Assert
        verify(repo, times(1)).deleteById(id);
    }

}
