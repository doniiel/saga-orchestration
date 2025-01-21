package com.dan1yal.notification_service.command;

import com.dan1yal.notification_service.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SendNotificationCommand {
    private String orderId;
    private BigDecimal amount;
    private Status status;
}
