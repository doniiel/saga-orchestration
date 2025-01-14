package com.dan1yal.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDto {
    private String orderId;
    private String productId;
    private String userId;
    private Integer quantity;
    private BigDecimal price;
}
