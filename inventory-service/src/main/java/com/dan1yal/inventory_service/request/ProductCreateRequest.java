package com.dan1yal.inventory_service.request;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
public class ProductCreateRequest {
    private String name;
    private Integer quantity;
    private BigDecimal price;
}
