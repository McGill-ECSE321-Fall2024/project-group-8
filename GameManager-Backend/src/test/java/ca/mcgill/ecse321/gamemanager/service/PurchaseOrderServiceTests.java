package ca.mcgill.ecse321.gamemanager.service;

import ca.mcgill.ecse321.gamemanager.dto.CategoryDto;
import ca.mcgill.ecse321.gamemanager.dto.PurchaseOrderDto;
import ca.mcgill.ecse321.gamemanager.model.Category;
import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder;
import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder.OrderStatus;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import ca.mcgill.ecse321.gamemanager.repository.PurchaseOrderRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
        PurchaseOrderDto orderDto = new PurchaseOrderDto(0, status, totalPrice, date);
        PurchaseOrder order = new PurchaseOrder(status, totalPrice, date);
        when(repo.save(any(PurchaseOrder.class))).thenReturn(order);

        // Act
        PurchaseOrderDto createdOrder = service.createOrder(orderDto);

        // Assert
        assertNotNull(createdOrder);
        assertEquals(status, createdOrder.getOrderStatus());
        assertEquals(totalPrice, createdOrder.getPrice());
        assertEquals(date, createdOrder.getDate());
        verify(repo, times(1)).save(any(PurchaseOrder.class));
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
        List<PurchaseOrderDto> result = service.getAllOrders();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(orders.stream().map(PurchaseOrder::getDate).collect(Collectors.toList()),
                result.stream().map(PurchaseOrderDto::getDate).collect(Collectors.toList()));
        assertEquals(orders.stream().map(PurchaseOrder::getOrderStatus).collect(Collectors.toList()),
                result.stream().map(PurchaseOrderDto::getOrderStatus).collect(Collectors.toList()));
        assertEquals(orders.stream().map(PurchaseOrder::getDate).collect(Collectors.toList()),
                result.stream().map(PurchaseOrderDto::getDate).collect(Collectors.toList()));
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

        // new info for updating
        OrderStatus newStatus = OrderStatus.ShoppingCart;
        double newPrice = 4321.99;
        Date newDate = Date.valueOf(LocalDate.now());

        PurchaseOrderDto updatedDto = new PurchaseOrderDto(id, newStatus, newPrice, newDate);

        when(repo.findByOrderId(id)).thenReturn(order);
        when(repo.save(any(PurchaseOrder.class))).thenReturn(order);

        // Act
        PurchaseOrderDto result = service.updateOrder(id, updatedDto);

        // Assert
        assertNotNull(result);
        assertEquals(newPrice, result.getPrice());
        assertEquals(newStatus, result.getOrderStatus());
        assertEquals(newDate, result.getDate());
        verify(repo, times(1)).findByOrderId(id);
        verify(repo, times(1)).save(any(PurchaseOrder.class));
    }

    // left to code:
    // findOrderById - valid and invalid
    // deleteOrder - valid and invalid

    /*
    tutorial examples:

    @Test
    public void testReadPersonByValidId() {
        // Arrange
        int id = 42;
        Person charlie = new Person("Charlie", "charlie@mail.mcgill.ca", "password123", Date.valueOf("2024-03-01"));
        when(repo.findPersonById(id)).thenReturn(charlie);

        // Act
        Person person = service.findPersonById(id);

        // Assert
        assertNotNull(person);
        assertEquals(charlie.getName(), person.getName());
        assertEquals(charlie.getEmail(), person.getEmail());
        assertEquals(charlie.getPassword(), person.getPassword());
        assertEquals(charlie.getCreationDate(), person.getCreationDate());
    }

    @Test
    public void testReadPersonByInvalidId() {
        // Set up
        int id = 42;
        // Default is to return null, so you could omit this
        when(repo.findPersonById(id)).thenReturn(null);

        // Act
        // Assert
        EventRegistrationException e = assertThrows(EventRegistrationException.class, () -> service.findPersonById(id));
        assertEquals("There is no person with ID " + id + ".", e.getMessage());
        // assertThrows is basically like the following:
        // try {
        // service.findPersonById(id);
        // fail("No exception was thrown.");
        // } catch (IllegalArgumentException e) {
        // assertEquals("There is no person with ID " + id + ".", e.getMessage());
        // }
    }

     */

}
