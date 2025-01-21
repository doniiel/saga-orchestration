package com.dan1yal.orderservice.event.payment;

import com.dan1yal.orderservice.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentFailedEvent {
    private String orderId;
    private BigDecimal amount;
    private Status status;
}
