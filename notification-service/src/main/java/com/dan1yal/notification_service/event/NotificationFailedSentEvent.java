package com.dan1yal.notification_service.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationFailedSentEvent {
    private String orderId;
    private String message;
}
