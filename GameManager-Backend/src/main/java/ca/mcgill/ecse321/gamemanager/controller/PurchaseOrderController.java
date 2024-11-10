package ca.mcgill.ecse321.gamemanager.controller;

import ca.mcgill.ecse321.gamemanager.dto.PurchaseOrderDto;
import ca.mcgill.ecse321.gamemanager.dto.PurchaseOrderRequestDto;
import ca.mcgill.ecse321.gamemanager.model.GameCopy;
import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder;
import ca.mcgill.ecse321.gamemanager.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
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
//        return new PurchaseOrderDto(id, orderRequestDto.getOrderStatus(), orderRequestDto.getPrice(), Date.valueOf(LocalDate.now()));
    }

    @PutMapping("/{id}/{game}")
    public PurchaseOrderDto addGameToCart(@PathVariable int id, @PathVariable List<Integer> gameCopyIds) {
        PurchaseOrder cart = orderService.addGameToCart(id, gameCopyIds);
        return convertToDto(cart);
    }

    @PostMapping("/{id}")
    public PurchaseOrderDto checkOut(@PathVariable int id){
        PurchaseOrder checkOutOrder = orderService.checkOut(id);
        return convertToDto(checkOutOrder);
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