package com.dan1yal.orderservice.exc;

public class OrderHistoryNotFound extends RuntimeException {
    public OrderHistoryNotFound(String message) {
        super(message);
    }
}
