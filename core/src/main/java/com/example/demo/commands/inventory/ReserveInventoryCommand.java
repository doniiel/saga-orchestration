package com.example.demo.commands.inventory;

import java.math.BigDecimal;

public class ReserveInventoryCommand {
    private String orderId;
    private String userId;
    private String productId;
    private Integer quantity;
    private BigDecimal amount;

    public ReserveInventoryCommand() {}

    public ReserveInventoryCommand(String orderId, String userId, String productId, Integer quantity, BigDecimal amount) {
        this.orderId = orderId;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
