package ca.mcgill.ecse321.gamemanager.dto;

import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder;
import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder.OrderStatus;

import java.util.Date;

public class PurchaseOrderDto {
    private int orderId;
    private OrderStatus orderStatus;
    private Date date;

    @SuppressWarnings("unused")
    private PurchaseOrderDto() {}

    public PurchaseOrderDto(PurchaseOrder model) {
        this.date = model.getDate();
        this.orderId = model.getOrderId();
        this.orderStatus = model.getOrderStatus();
    }

    // Getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getOrderStatus() {return orderStatus; }

    public void setOrderStatus(OrderStatus status) { this.orderStatus = status; }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
