package com.dan1yal.inventory_service.exc;

import lombok.Data;

@Data
public class ProductInsufficientQuantityException extends RuntimeException{
    private final String productId;
    private final Integer quantity;
}
