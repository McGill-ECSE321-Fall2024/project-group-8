package ca.mcgill.ecse321.gamemanager.service;

import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder;
import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder.OrderStatus;
import ca.mcgill.ecse321.gamemanager.repository.PurchaseOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseOrderService {
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    // Find all orders
    public List<PurchaseOrder> getAllOrders() {
        return (List<PurchaseOrder>) purchaseOrderRepository.findAll();
    }

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
    public PurchaseOrder createOrder(OrderStatus aOrderStatus, double aTotalPrice) {
        if (aTotalPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        if (aOrderStatus == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        Date now = Date.valueOf(LocalDate.now());
        PurchaseOrder newOrder = new PurchaseOrder(aOrderStatus, aTotalPrice, now);

        return purchaseOrderRepository.save(newOrder);
    }

    // Update an order, especially the price / status
    @Transactional
    public PurchaseOrder updateOrder(int id, OrderStatus status, double price, Date date) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        PurchaseOrder order = purchaseOrderRepository.findByOrderId(id);
        if (order != null) {
            order.setDate(date);
            order.setTotalPrice(price);
            order.setOrderStatus(status);
            purchaseOrderRepository.save(order);
        }

        return order;
    }


    // Delete an order by ID
    @Transactional
    public void deleteOrder(int id) {
        if (purchaseOrderRepository.findByOrderId(id) == null) {
            throw new IllegalArgumentException("Order with id " + id + " does not exist.");
        }
        purchaseOrderRepository.deleteById(id);
    }
}