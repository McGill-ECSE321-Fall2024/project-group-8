package ca.mcgill.ecse321.gamemanager.dto;

import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder;

public class PurchaseOrderRequestDto {
    private PurchaseOrder.OrderStatus orderStatus;
    private double price;

    public PurchaseOrderRequestDto(PurchaseOrder.OrderStatus status, double price) {
        this.orderStatus = status;
        this.price = price;
    }

    public PurchaseOrder.OrderStatus getOrderStatus() { return orderStatus; }

    public double getPrice() {return price;}

}
