package com.example.demo.events.payment;
import com.example.demo.enums.Status;

import java.math.BigDecimal;

public class PaymentFailedEvent {
    private String orderId;
    private String productId;
    private Integer quantity;
    private BigDecimal amount;
    private Status status;

    public PaymentFailedEvent() {}

    public PaymentFailedEvent(String orderId, String productId, Integer quantity, BigDecimal amount, Status status) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
}
