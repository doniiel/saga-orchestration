package com.dan1yal.payment_service.handler;

import com.dan1yal.payment_service.command.ProcessPaymentCommand;
import com.dan1yal.payment_service.enums.Status;
import com.dan1yal.payment_service.event.PaymentFailedEvent;
import com.dan1yal.payment_service.event.PaymentProcessedEvent;
import com.dan1yal.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = "${payment.command.topic-name}", groupId = "${spring.kafka.consumer.group-id}")
public class PaymentCommandHandler {
    private final PaymentService paymentService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${payment.event.topic-name}")
    private String paymentEventTopicName;

    @KafkaHandler
    public void handleCommand(ProcessPaymentCommand command) {
        try {
            paymentService.processPayment(command.getOrderId(), command.getAmount());
            processSuccessfulPayment(command);
            log.info("Payment processed successfully. Order ID: {}, ", command.getOrderId());
        } catch (Exception e) {
            log.error("Error processing payment for Order ID: {}", command.getOrderId(), e);
            processFailedPayment(command);
            log.info("Payment failed for Order ID: {}. Reason: {}", command.getOrderId(), e.getMessage());
        }
    }

    private void processSuccessfulPayment(ProcessPaymentCommand command) {
        PaymentProcessedEvent event = PaymentProcessedEvent.builder()
                .orderId(command.getOrderId())
                .amount(command.getAmount())
                .status(Status.SUCCESS)
                .build();
        kafkaTemplate.send(paymentEventTopicName, event);
    }

    private void processFailedPayment(ProcessPaymentCommand command) {
        PaymentFailedEvent event = PaymentFailedEvent.builder()
                .orderId(command.getOrderId())
                .amount(command.getAmount())
                .status(Status.FAILED)
                .build();
        kafkaTemplate.send(paymentEventTopicName, event);
    }
}
