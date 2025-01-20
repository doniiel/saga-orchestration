package com.dan1yal.notification_service.exc;

public class NotRetryableException extends RuntimeException {
    public NotRetryableException(String message) {
        super(message);
    }

    public NotRetryableException(Exception exception) {
        super(exception);
    }
}
