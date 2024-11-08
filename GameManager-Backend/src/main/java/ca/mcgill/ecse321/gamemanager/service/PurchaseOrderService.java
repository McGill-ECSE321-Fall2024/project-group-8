package ca.mcgill.ecse321.gamemanager.service;


import ca.mcgill.ecse321.gamemanager.dto.CategoryDto;
import ca.mcgill.ecse321.gamemanager.dto.PurchaseOrderDto;
import ca.mcgill.ecse321.gamemanager.model.Category;
import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder;
import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder.OrderStatus;
import ca.mcgill.ecse321.gamemanager.repository.PurchaseOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    // Getting all orders
    @Transactional
    public List<PurchaseOrderDto> getAllOrders() {
        return ((List<PurchaseOrder>) purchaseOrderRepository.findAll())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Finding orders with its id
    public PurchaseOrderDto findOrderById(int id) {
        PurchaseOrder order = purchaseOrderRepository.findByOrderId(id);
        if (order == null) {
            throw new IllegalArgumentException("There is no order with ID " + id + ".");
        }

        PurchaseOrderDto returnedOrder =
                new PurchaseOrderDto(id, order.getOrderStatus(), order.getTotalPrice(), order.getDate());

        return returnedOrder;
    }

    // Creating an order w/ validation
    @Transactional
    public PurchaseOrderDto createOrder(PurchaseOrderDto orderDto) {
        if (orderDto.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (orderDto.getOrderStatus() == null) {
            throw new IllegalArgumentException("There needs to be a status");
        }

        Date now = Date.valueOf(LocalDate.now());
        PurchaseOrder newOrder = new PurchaseOrder(orderDto.getOrderStatus(), orderDto.getPrice(), now);
        newOrder = purchaseOrderRepository.save(newOrder);
        return convertToDto(newOrder);
    }

    // Update an order, especially the price / status
    @Transactional
    public PurchaseOrderDto updateOrder(int id, PurchaseOrderDto orderDto) {
        if (orderDto.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        if (orderDto.getDate() == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        if (orderDto.getOrderStatus() == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        PurchaseOrder order = purchaseOrderRepository.findByOrderId(id);
        order.setDate(orderDto.getDate());
        order.setTotalPrice(orderDto.getPrice());
        order.setOrderStatus(orderDto.getOrderStatus());
        purchaseOrderRepository.save(order);

        return convertToDto(order);
    }


    // Delete an order by ID
    @Transactional
    public void deleteOrder(int id) {
        if (!purchaseOrderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with ID " + id + " not found");
        }
        purchaseOrderRepository.deleteById(id);
    }

    // Converting to Dto
    private PurchaseOrderDto convertToDto(PurchaseOrder order) {
        return new PurchaseOrderDto(order.getOrderId(), order.getOrderStatus(),
                order.getTotalPrice(), order.getDate());
    }
}
