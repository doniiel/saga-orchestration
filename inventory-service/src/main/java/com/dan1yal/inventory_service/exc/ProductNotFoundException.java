package com.dan1yal.inventory_service.exc;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
    public ProductNotFoundException(Exception exception) {
        super(exception);
    }
}
