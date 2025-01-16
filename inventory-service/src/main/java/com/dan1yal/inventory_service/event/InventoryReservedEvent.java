package com.dan1yal.inventory_service.event;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InventoryReservedEvent {
    private String orderId;
    private String productId;
    private Integer quantity;
    private BigDecimal amount;
}
