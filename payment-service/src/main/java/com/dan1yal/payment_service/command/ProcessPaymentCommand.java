package com.dan1yal.payment_service.command;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
@Builder
public class ProcessPaymentCommand {
    private String orderId;
    private BigDecimal amount;
}
