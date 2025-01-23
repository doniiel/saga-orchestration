package com.dan1yal.payment_service.service.impl;

import com.dan1yal.payment_service.service.PaymentService;
import jakarta.websocket.OnError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Override
    public void processPayment(String orderId, BigDecimal amount) {
        log.info("Processing payment for Order ID: {}, Amount: {}", orderId, amount);

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Payment failed for Order ID: {}, Invalid amount: {}", orderId, amount);
            throw new IllegalArgumentException("Payment amount must be greater than zero");
        }
        try {
            simulateExternalPaymentProcessing(orderId, amount);
            log.info("Payment successfully processed for Order ID: {}, Amount: {}", orderId, amount);
        } catch (Exception e) {
            log.error("Payment processing failed for Order ID: {}", orderId, e);
            throw new RuntimeException("Payment processing error: " + e.getMessage());
        }
    }

    @Override
    public void cancelPayment(String orderId) {
        log.info("Cancelling payment for Order ID: {}", orderId);
    }

    private void simulateExternalPaymentProcessing(String orderId, BigDecimal amount) throws InterruptedException {
        Thread.sleep(1000);
        if (Math.random() < 0.1) {
            throw new RuntimeException("External payment gateway error");
        }
    }

}
