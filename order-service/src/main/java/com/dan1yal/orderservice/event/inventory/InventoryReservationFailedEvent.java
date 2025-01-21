package com.dan1yal.orderservice.event.inventory;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InventoryReservationFailedEvent {
    private String orderId;
    private String productId;
    private Integer quantity;
    private BigDecimal amount;
}
