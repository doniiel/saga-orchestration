package com.dan1yal.orderservice.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderCommand {
    private String orderId;
    private String productId;
    private String userId;
    private Integer quantity;
    private BigDecimal price;
}
