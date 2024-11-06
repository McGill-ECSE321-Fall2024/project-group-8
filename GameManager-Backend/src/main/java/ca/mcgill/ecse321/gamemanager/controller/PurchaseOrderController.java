package ca.mcgill.ecse321.gamemanager.controller;

import ca.mcgill.ecse321.gamemanager.dto.PurchaseOrderDto;
import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder;
import ca.mcgill.ecse321.gamemanager.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/purchaseOrders")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService orderService;

    // Get all orders
    @GetMapping
    public List<PurchaseOrderDto> getAllOrders() {
        return orderService.getAllOrders().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Get order by Id
    @GetMapping("/orders/{id}")
    public PurchaseOrderDto getOrderById(@PathVariable Integer id) {
        PurchaseOrder order = orderService.findOrderById(id);
        return convertToDto(order);
    }

    // Create a new order
    @PostMapping("/orders")
    public PurchaseOrderDto createOrder(@RequestBody PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrder createdOrder = orderService.createOrder(
                purchaseOrderDto.getOrderStatus(),
                purchaseOrderDto.getPrice()
        );

        return convertToDto(createdOrder);
    }

    // Update order by ID
    @PutMapping("/orders/{id}")
    public PurchaseOrderDto updateOrder(@PathVariable Integer id, @RequestBody PurchaseOrderDto orderUpdate) {
        PurchaseOrder updatedOrder = orderService.updateOrder(
                id,
                orderUpdate.getOrderStatus(),
                orderUpdate.getPrice(),
                orderUpdate.getDate());
        return convertToDto(updatedOrder);
    }

    // Delete order by ID
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
    }

    private PurchaseOrderDto convertToDto(PurchaseOrder order) {
        return new PurchaseOrderDto(order.getOrderId(), order.getOrderStatus(), order.getTotalPrice(), order.getDate());
    }
}
