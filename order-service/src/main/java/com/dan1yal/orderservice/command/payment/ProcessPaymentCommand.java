package com.dan1yal.orderservice.command.payment;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProcessPaymentCommand {
    private String orderId;
    private BigDecimal amount;
}
