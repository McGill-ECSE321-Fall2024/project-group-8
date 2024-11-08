package ca.mcgill.ecse321.gamemanager.dto;

import ca.mcgill.ecse321.gamemanager.model.PurchaseOrder;

import java.sql.Date;

public class PurchaseOrderRequestDto {
    private int orderId;
    private PurchaseOrder.OrderStatus orderStatus;
    private Date date;
    private double price;

    public PurchaseOrderRequestDto(int id, PurchaseOrder.OrderStatus status, double price, Date date) {
        this.orderId = id;
        this.orderStatus = status;
        this.price = price;
        this.date = date;
    }

    public int getOrderId() {
        return orderId;
    }

    public PurchaseOrder.OrderStatus getOrderStatus() { return orderStatus; }

    public Date getDate() {
        return date;
    }

    public double getPrice() {return price;}

}
