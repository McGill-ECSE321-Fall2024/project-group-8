package ca.mcgill.ecse321.gamemanager.controller;

import ca.mcgill.ecse321.gamemanager.dto.PurchaseOrderDto;
import ca.mcgill.ecse321.gamemanager.dto.PurchaseOrderRequestDto;
import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder;
import ca.mcgill.ecse321.gamemanager.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
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
    @GetMapping("/{id}")
    public PurchaseOrderDto findOrderById(@PathVariable int id) {
        PurchaseOrder order = orderService.findOrderById(id);

        return convertToDto(order);
    }

    // Create a new order
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseOrderDto createOrder(@RequestBody PurchaseOrderRequestDto orderRequestDto) {
        PurchaseOrder createdOrder =
                orderService.createOrder(orderRequestDto.getOrderStatus(), orderRequestDto.getPrice());
        return convertToDto(createdOrder);
    }

    // Update order by ID
    @PutMapping("/{id}")
    public PurchaseOrderDto updateOrder(@PathVariable int id, @RequestBody PurchaseOrderRequestDto orderRequestDto) {
        PurchaseOrder updatedOrder = orderService.updateOrder(
                id,
                orderRequestDto.getOrderStatus(),
                orderRequestDto.getPrice());
        return convertToDto(updatedOrder);
    }

    @PutMapping("/{id}/cart")
    public PurchaseOrderDto addGameToCart(@PathVariable int id, @RequestBody List<Integer> gameCopyIds) {
        PurchaseOrder cart = orderService.addGameToCart(id, gameCopyIds);
        PurchaseOrderDto orderDto = new PurchaseOrderDto(cart);
        return orderDto;
    }

    @PostMapping("/{id}")
    public PurchaseOrderDto checkOut(@PathVariable int id){
        PurchaseOrder checkOutOrder = orderService.checkOut(id);
        return new PurchaseOrderDto(checkOutOrder);
    }
    // Delete order by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
    }

    private PurchaseOrderDto convertToDto(PurchaseOrder order) {
        return new PurchaseOrderDto(order.getOrderId(), order.getOrderStatus(), order.getTotalPrice(), order.getDate());
    }
}