package com.dan1yal.orderservice.exc;

public class RetryableException extends RuntimeException {

    public RetryableException(Exception exception) {
        super(exception);
    }
    public RetryableException(String message) {
        super(message);
    }
}
