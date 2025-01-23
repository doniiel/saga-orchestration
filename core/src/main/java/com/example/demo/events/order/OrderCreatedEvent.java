package com.example.demo.events.order;

import java.math.BigDecimal;

public class OrderCreatedEvent {
    private String orderId;
    private String productId;
    private String userId;
    private Integer quantity;
    private BigDecimal price;

    public OrderCreatedEvent() {}

    public OrderCreatedEvent(String orderId, String productId, String userId, Integer quantity, BigDecimal price) {
        this.orderId = orderId;
        this.productId = productId;
        this.userId = userId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
