package com.dan1yal.orderservice.command.notification;

import com.dan1yal.orderservice.enums.Status;
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
