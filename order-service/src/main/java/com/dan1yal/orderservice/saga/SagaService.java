package com.dan1yal.orderservice.saga;

import com.example.demo.commands.notification.SendNotificationCommand;
import com.example.demo.commands.order.CompleteOrderCommand;
import com.example.demo.commands.inventory.CancelReserveInventoryCommand;
import com.example.demo.commands.payment.CancelPaymentCommand;
import com.example.demo.commands.payment.ProcessPaymentCommand;
import com.example.demo.commands.inventory.ReserveInventoryCommand;
import com.example.demo.commands.order.CancelOrderCommand;
import com.example.demo.events.notification.NotificationFailedSentEvent;
import com.example.demo.events.notification.NotificationSentEvent;
import com.example.demo.events.order.OrderCreatedEvent;
import com.example.demo.events.inventory.InventoryReservationFailedEvent;
import com.example.demo.events.inventory.InventoryReservedEvent;
import com.example.demo.events.payment.PaymentFailedEvent;
import com.example.demo.events.payment.PaymentProcessedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Component
@KafkaListener(topics = {
        "${orders.event.topic-name}",
        "${inventory.event.topic-name}",
        "${payment.event.topic-name}",
        "${notification.event.topic-name}"
})
@RequiredArgsConstructor
@Transactional
public class SagaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${orders.command.topic-name}")
    private String orderCommandTopicName;
    @Value("${inventory.command.topic-name}")
    private String inventoryCommandTopicName;
    @Value("${payment.command.topic-name}")
    private String paymentCommandTopicName;
    @Value("${notification.command.topic-name}")
    private String notificationCommandTopicName;

    @KafkaHandler
    public void handleEvent(@Payload OrderCreatedEvent event) {
        log.info("Received OrderCreatedEvent: {}", event);
        BigDecimal totalCost = event.getPrice().multiply(BigDecimal.valueOf(event.getQuantity()));

        ReserveInventoryCommand command =  new ReserveInventoryCommand(
                event.getOrderId(),
                event.getUserId(),
                event.getProductId(),
                event.getQuantity(),
                totalCost
        );

        log.info("Sending ReserveInventoryCommand: {}", command);
        kafkaTemplate.send(inventoryCommandTopicName, command);
    }

    @KafkaHandler
    public void handleInventoryReserved(@Payload InventoryReservedEvent event) {
        log.info("Received InventoryReservedEvent: {}", event);
        ProcessPaymentCommand command = new ProcessPaymentCommand(
                event.getOrderId(),
                event.getProductId(),
                event.getQuantity(),
                event.getAmount()
                );
        log.info("Sending ProcessPaymentCommand: {}", command);
        kafkaTemplate.send(paymentCommandTopicName, command);
    }

    @KafkaHandler
    public void hanldeInverntoryReservationFailed(@Payload InventoryReservationFailedEvent event) {
        log.info("Received InventoryReservationFailedEvent: {}", event);
        CancelOrderCommand command = new CancelOrderCommand(
                event.getOrderId()
        );
        log.info("Sending CancelOrderCommand: {}", command);
        kafkaTemplate.send(orderCommandTopicName, command);
    }

    @KafkaHandler
    public void handlePaymentProcessed(@Payload PaymentProcessedEvent event) {
        log.info("Received PaymentProcessedEvent: {}", event);
        SendNotificationCommand command = new SendNotificationCommand(
                event.getOrderId(),
                event.getProductId(),
                event.getQuantity(),
                event.getAmount(),
                event.getStatus()
                );
        log.info("Sending SendNotificationCommand: {}", command);
        kafkaTemplate.send(notificationCommandTopicName,command);
    }

    @KafkaHandler
    public void handlePaymentFailed(@Payload PaymentFailedEvent event) {
        log.info("Received PaymentFailedEvent: {}", event);
        CancelReserveInventoryCommand command = new CancelReserveInventoryCommand(
                event.getOrderId(),
                event.getProductId(),
                event.getQuantity(),
                event.getAmount()
        );
        log.info("Sending CancelReserveInventoryCommand: {}", command);
        kafkaTemplate.send(inventoryCommandTopicName, command);
    }


    @KafkaHandler
    public void handleNotificationSent(@Payload NotificationSentEvent event) {
        CompleteOrderCommand command = new CompleteOrderCommand(
                event.getOrderId(),
                event.getProductId(),
                event.getQuantity(),
                event.getAmount()
        );

        log.info("Sending CompleteOrderCommand: {}", command);
        kafkaTemplate.send(orderCommandTopicName,command);
    }

    @KafkaHandler
    public void handleNotificationFailed(@Payload NotificationFailedSentEvent event) {
        CancelPaymentCommand command = new CancelPaymentCommand(
                event.getOrderId(),
                event.getProductId(),
                event.getQuantity(),
                event.getAmount()
        );
        log.info("Sending CancelPaymentCommand: {}", command);
        kafkaTemplate.send(paymentCommandTopicName,command);
    }
}
