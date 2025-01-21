package com.dan1yal.orderservice.saga;

import com.dan1yal.orderservice.command.inventory.ReserveInventoryCommand;
import com.dan1yal.orderservice.command.notification.SendNotificationCommand;
import com.dan1yal.orderservice.command.order.CancelOrderCommand;
import com.dan1yal.orderservice.command.order.CompleteOrderCommand;
import com.dan1yal.orderservice.command.payment.ProcessPaymentCommand;
import com.dan1yal.orderservice.event.inventory.InventoryReservedEvent;
import com.dan1yal.orderservice.event.notification.NotificationSentEvent;
import com.dan1yal.orderservice.event.order.OrderCompletedEvent;
import com.dan1yal.orderservice.event.order.OrderCreatedEvent;
import com.dan1yal.orderservice.event.payment.PaymentProcessedEvent;
import com.dan1yal.orderservice.service.OrderHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

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
public class SagaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderHistoryService orderHistoryService;
    @Value("${orders.command.topic-name}")
    private String orderCommandTopicName;
    @Value("${inventory.command.topic-name}")
    private String inventoryCommandTopicName;
    @Value("${payment.command.topic-name}")
    private String paymentCommandTopicName;
    @Value("${notification.command.topic-name}")
    private String notificationCommandTopicName;

    @KafkaHandler
    public void handleOrderCreated(@Payload OrderCreatedEvent event) {
        BigDecimal totalCost = event.getPrice().multiply(BigDecimal.valueOf(event.getQuantity()));

        ReserveInventoryCommand command = ReserveInventoryCommand.builder()
                .orderId(event.getOrderId())
                .userId(event.getUserId())
                .amount(totalCost)
                .build();

        log.info("Sending ReserveInventoryCommand: {}", command);
        kafkaTemplate.send(inventoryCommandTopicName, command);
    }

    @KafkaHandler
    public void handleInventoryReserved(@Payload InventoryReservedEvent event) {
        ProcessPaymentCommand command = ProcessPaymentCommand.builder()
                .orderId(event.getOrderId())
                .amount(event.getAmount())
                .build();
        log.info("Sending ProcessPaymentCommand: {}", command);
        kafkaTemplate.send(paymentCommandTopicName, command);
    }

    @KafkaHandler
    public void handleInventoryReservationFailed(@Payload InventoryReservedEvent event) {
        CancelOrderCommand command = CancelOrderCommand.builder().build();
        log.info("Sending CancelOrderCommand: {}", command);
        kafkaTemplate.send(orderCommandTopicName, command);
    }

    @KafkaHandler
    public void handlePaymentProcessed(@Payload PaymentProcessedEvent event) {
        SendNotificationCommand command = SendNotificationCommand.builder()
                .orderId(event.getOrderId())
                .amount(event.getAmount())
                .status(event.getStatus())
                .build();
        log.info("Sending SendNotificationCommand: {}", command);
        kafkaTemplate.send(notificationCommandTopicName,command);
    }


    @KafkaHandler
    public void handleNotificationSent(@Payload NotificationSentEvent event) {
        CompleteOrderCommand command = CompleteOrderCommand.builder()
                .orderId(event.getOrderId())
                .build();

        log.info("Sending CompleteOrderCommand: {}", command);
        kafkaTemplate.send(orderCommandTopicName,command);
    }

    @KafkaHandler
    public void handler(@Payload OrderCompletedEvent event) {
        log.info("Order processing completed successfully:");
//        orderHistoryService.updateOrderStatus(event.getOrderId(), OrderStatus.COMPLETED);
    }
}
