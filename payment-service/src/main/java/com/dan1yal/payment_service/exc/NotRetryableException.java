package com.dan1yal.payment_service.exc;

public class NotRetryableException extends RuntimeException {
    public NotRetryableException(String message) {
        super(message);
    }
    public NotRetryableException(Exception exception) {
        super(exception);
    }
}
