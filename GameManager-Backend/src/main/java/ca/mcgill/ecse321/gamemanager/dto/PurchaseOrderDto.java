package ca.mcgill.ecse321.gamemanager.dto;

import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder.OrderStatus;

import java.sql.Date;

public class PurchaseOrderDto {
    private int orderId;
    private OrderStatus orderStatus;
    private Date date;
    private double price;

    @SuppressWarnings("unused")
    private PurchaseOrderDto() {}

    public PurchaseOrderDto(int id, OrderStatus status, double price, Date date) {
        this.orderId = id;
        this.orderStatus = status;
        this.price = price;
        this.date = date;
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

    public double getPrice() {return price;}

    public void setPrice(double price) { this.price = price; }
}
