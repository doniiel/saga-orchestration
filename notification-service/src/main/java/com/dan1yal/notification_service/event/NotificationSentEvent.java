package com.dan1yal.notification_service.event;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NotificationSentEvent {
    private String orderId;
    private String message;
}
