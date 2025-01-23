package com.example.demo.events.notification;


import java.math.BigDecimal;

public class NotificationSentEvent {
    private String orderId;
    private String message;
    private String productId;
    private Integer quantity;
    private BigDecimal amount;

    public NotificationSentEvent() {}

    public NotificationSentEvent(String orderId, String message, String productId, Integer quantity, BigDecimal amount) {
        this.orderId = orderId;
        this.message = message;
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
