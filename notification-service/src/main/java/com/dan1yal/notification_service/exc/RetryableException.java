package com.dan1yal.notification_service.exc;

public class RetryableException extends RuntimeException {
    public RetryableException(String message) {
        super(message);
    }
    public RetryableException(Exception exception) {
        super(exception);
    }
}
