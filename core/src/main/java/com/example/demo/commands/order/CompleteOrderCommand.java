package com.example.demo.commands.order;

import java.math.BigDecimal;

public class CompleteOrderCommand {
    private String orderId;

    public CompleteOrderCommand(String orderId, String productId, Integer quantity, BigDecimal amount) {}

    public CompleteOrderCommand(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
