package ca.mcgill.ecse321.gamemanager.service;

import ca.mcgill.ecse321.gamemanager.model.GameCopy;
import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder;
import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder.OrderStatus;
import ca.mcgill.ecse321.gamemanager.repository.GameCopyRepository;
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
    @Autowired
    private GameCopyRepository gameCopyRepository;

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
            throw new IllegalArgumentException("Price cannot be negative.");
        }

        if (aOrderStatus == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }

        Date now = Date.valueOf(LocalDate.now());
        PurchaseOrder newOrder = new PurchaseOrder(aOrderStatus, aTotalPrice, now);

        return purchaseOrderRepository.save(newOrder);
    }

    // Update an order, especially the price / status
    @Transactional
    public PurchaseOrder updateOrder(int id, OrderStatus status, double price, Date date) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }

        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null.");
        }

        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
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

    @Transactional
    public PurchaseOrder addGameToCart(int purchaseOrderId, List<Integer> gameCopyIds) {
        PurchaseOrder order = purchaseOrderRepository.findByOrderId(purchaseOrderId);
        if (order == null) { throw new IllegalArgumentException("Invalid order Id.");}
        if (gameCopyIds.isEmpty()){ throw new IllegalArgumentException("Can not add empty Game in cart.");
        }
        for (int id : gameCopyIds) {
            GameCopy gameCopy = gameCopyRepository.findGameCopyByGameCopyId(id);
            if (gameCopy==null) {
                int index = gameCopyIds.indexOf(id);
                throw  new IllegalArgumentException(String.format("Invalid gameCopy id contained at %dth id.", index));
            }
            order.addGameCopy(gameCopy);
        }
        return purchaseOrderRepository.save(order);
    }

    @Transactional
    public PurchaseOrder checkOut(int purchaseOrderId) {
        PurchaseOrder order = purchaseOrderRepository.findByOrderId(purchaseOrderId);
        if (order == null) {
            throw new IllegalArgumentException("Invalid order Id.");
        }
        List<GameCopy> gameCopies= order.getGameCopies();
        if (gameCopies.isEmpty()) {
            throw new IllegalStateException("Can not check out without game in the order.");
        }
        order.setOrderStatus(OrderStatus.Bought);
        return purchaseOrderRepository.save(order);
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