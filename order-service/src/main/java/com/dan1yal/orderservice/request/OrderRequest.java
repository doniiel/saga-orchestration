package com.dan1yal.orderservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String productId;
    private String userId;
    private Integer quantity;
    private BigDecimal price;
}
