package ca.mcgill.ecse321.gamemanager.controller;

import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder;
import ca.mcgill.ecse321.gamemanager.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/purchaseOrders")
public class PurchaseOrderController {
    // template from Nara

    @Autowired
    private PurchaseOrderRepository orderRepo;

    // Get all orders
    @GetMapping
    public List<PurchaseOrder> getAllOrders() {
        return StreamSupport.stream(orderRepo.findAll().spliterator(), false).collect(Collectors.toList());
    }

    // Get order by Id
    @GetMapping("/orders/{id}")
    public ResponseEntity<PurchaseOrder> getOrderById(@PathVariable Integer id) {
        Optional<PurchaseOrder> purchaseOrder = orderRepo.findById(id);
        return purchaseOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Create a new order
    @PostMapping("/orders")
    public ResponseEntity<PurchaseOrder> createOrder(@RequestBody PurchaseOrder purchaseOrder) {
        PurchaseOrder savedOrder = orderRepo.save(purchaseOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    // Update order by ID
    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrder> updateOrder(@PathVariable Integer id, @RequestBody PurchaseOrder orderUpdate) {
        Optional<PurchaseOrder> purchaseOrder = orderRepo.findById(id);
        if (purchaseOrder.isPresent()) {
            PurchaseOrder order = purchaseOrder.get();
            order.setOrderStatus(orderUpdate.getOrderStatus());
            order.setTotalPrice(orderUpdate.getTotalPrice());
            order.setDate(orderUpdate.getDate());
            return ResponseEntity.ok(orderRepo.save(order));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete order by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        if (orderRepo.existsById(id)) {
            orderRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
