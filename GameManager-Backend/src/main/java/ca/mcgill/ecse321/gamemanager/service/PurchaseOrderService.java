package ca.mcgill.ecse321.gamemanager.service;


import ca.mcgill.ecse321.gamemanager.dto.CategoryDto;
import ca.mcgill.ecse321.gamemanager.dto.PurchaseOrderDto;
import ca.mcgill.ecse321.gamemanager.model.Category;
import ca.mcgill.ecse321.gamemanager.model.Game;
import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder;
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

    // Finding orders with its id
    @Transactional
    public PurchaseOrder findOrderById(int id) {
        PurchaseOrder order = purchaseOrderRepository.findByOrderId(id);
        if (order == null) {
            throw new IllegalArgumentException("There is no order with ID " + id + ".");
        }
        return order;
    }

    // Creating an order w/ validation
    @Transactional
    public PurchaseOrder createOrder(PurchaseOrder.OrderStatus aOrderStatus, double aTotalPrice) {
        if (aTotalPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        Date now = Date.valueOf(LocalDate.now());
        PurchaseOrder newOrder = new PurchaseOrder(aOrderStatus, aTotalPrice, now);

        return purchaseOrderRepository.save(newOrder);
    }

    // Update an order, especially the price / status
    @Transactional
    public PurchaseOrder updateOrder(int id, PurchaseOrder.OrderStatus status, double price, Date date) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        PurchaseOrder order = purchaseOrderRepository.findByOrderId(id);
        double oldPrice = order.getTotalPrice();
        order.setDate(date);
        order.setTotalPrice(oldPrice + price);
        order.setOrderStatus(status);

        return purchaseOrderRepository.save(order);
    }


    // Delete an order by ID
    @Transactional
    public void deleteOrder(int id) {
        if (!purchaseOrderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with ID " + id + " not found");
        }
        purchaseOrderRepository.deleteById(id);
    }
}
