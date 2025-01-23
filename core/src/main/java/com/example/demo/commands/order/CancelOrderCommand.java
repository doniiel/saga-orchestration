package com.example.demo.commands.order;

public class CancelOrderCommand {
    private String orderId;

    public CancelOrderCommand() {}

    public CancelOrderCommand(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
