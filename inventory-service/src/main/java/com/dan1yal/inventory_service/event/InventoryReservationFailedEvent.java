package com.dan1yal.inventory_service.event;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class InventoryReservationFailedEvent {
    private String orderId;
    private String productId;
    private Integer quantity;
    private BigDecimal amount;
}
