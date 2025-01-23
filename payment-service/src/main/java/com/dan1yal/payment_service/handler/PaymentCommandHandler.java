package com.dan1yal.payment_service.handler;

import com.dan1yal.payment_service.service.PaymentService;
import com.example.demo.commands.payment.CancelPaymentCommand;
import com.example.demo.commands.payment.ProcessPaymentCommand;
import com.example.demo.enums.Status;
import com.example.demo.events.payment.PaymentFailedEvent;
import com.example.demo.events.payment.PaymentProcessedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@KafkaListener(topics = "${payment.command.topic-name}")
public class PaymentCommandHandler {
    private final PaymentService paymentService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${payment.event.topic-name}")
    private String paymentEventTopicName;

    @KafkaHandler
    public void handleCommand(@Payload ProcessPaymentCommand command) {
        try {
            log.info("Processing payment for Order ID: {}", command.getOrderId());
            paymentService.processPayment(command.getOrderId(), command.getAmount());
            processSuccessfulPayment(command);
            log.info("Payment processed successfully. Order ID: {}, ", command.getOrderId());
        } catch (Exception e) {
            log.error("Error processing payment for Order ID: {}", command.getOrderId(), e);
            processFailedPayment(command);
            log.info("Payment failed for Order ID: {}. Reason: {}", command.getOrderId(), e.getMessage());
        }
    }

    @KafkaHandler
    public void handleCommand(@Payload CancelPaymentCommand command) {
        paymentService.cancelPayment(command.getOrderId());
        log.info("Payment canceled successfully for Order ID: {}", command.getOrderId());
        processFailedPayment(command);

    }

    private void processSuccessfulPayment(ProcessPaymentCommand command) {
        PaymentProcessedEvent event = new PaymentProcessedEvent(
                command.getOrderId(),
                command.getProductId(),
                command.getQuantity(),
                command.getAmount(),
                Status.SUCCESS
        );
        kafkaTemplate.send(paymentEventTopicName, event);
    }

    private void processFailedPayment(ProcessPaymentCommand command) {
        PaymentFailedEvent event = new PaymentFailedEvent(
                command.getOrderId(),
                command.getProductId(),
                command.getQuantity(),
                command.getAmount(),
                Status.FAILED
                );
        kafkaTemplate.send(paymentEventTopicName, event);
    }
    private void processFailedPayment(CancelPaymentCommand command) {
        PaymentFailedEvent event = new PaymentFailedEvent(
                command.getOrderId(),
                command.getProductId(),
                command.getQuantity(),
                command.getAmount(),
                Status.FAILED
                );
        kafkaTemplate.send(paymentEventTopicName, event);
    }
}
