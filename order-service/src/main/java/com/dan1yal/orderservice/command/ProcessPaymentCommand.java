package com.dan1yal.orderservice.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessPaymentCommand {
    private String orderId;
    private String userId;
    private BigDecimal amount;
}
