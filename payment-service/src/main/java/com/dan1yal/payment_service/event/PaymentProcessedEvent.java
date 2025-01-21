package com.dan1yal.payment_service.event;

import com.dan1yal.payment_service.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentProcessedEvent {
    private String orderId;
    private BigDecimal amount;
    private Status status;
}
