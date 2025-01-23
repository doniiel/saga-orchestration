package com.example.demo.commands.payment;

import java.math.BigDecimal;

public class CancelPaymentCommand {
    private String orderId;
    private String productId;
    private Integer quantity;
    private BigDecimal amount;

    public CancelPaymentCommand() {}
    public CancelPaymentCommand(String orderId, String productId, Integer quantity, BigDecimal amount) {
        this.orderId = orderId;
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
