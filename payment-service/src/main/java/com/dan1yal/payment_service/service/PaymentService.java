package com.dan1yal.payment_service.service;

import java.math.BigDecimal;

public interface PaymentService {
    void processPayment(String orderId, BigDecimal amount);

    void cancelPayment(String orderId);
}
