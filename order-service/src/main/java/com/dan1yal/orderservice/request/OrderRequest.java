package com.dan1yal.orderservice.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderRequest {
    private String productId;
    private String userId;
    private Integer quantity;
    private BigDecimal price;
}
