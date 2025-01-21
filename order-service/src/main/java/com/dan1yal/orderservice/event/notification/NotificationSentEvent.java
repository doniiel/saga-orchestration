package com.dan1yal.orderservice.event.notification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationSentEvent {
    private String orderId;
    private String message;

}
