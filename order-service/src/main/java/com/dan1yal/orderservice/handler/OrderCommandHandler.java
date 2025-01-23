package com.dan1yal.orderservice.handler;

import com.example.demo.commands.order.CompleteOrderCommand;
import com.dan1yal.orderservice.service.OrderService;
import com.example.demo.commands.order.CancelOrderCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@KafkaListener(topics = {"${order.command.topic-name}"})
public class OrderCommandHandler {

    private final OrderService orderService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${order.event.topic-name}")
    private String orderEventTopicName;

    @KafkaHandler
    public void handleCancelOrder(@Payload CancelOrderCommand command) {
        orderService.cancelOrder(command.getOrderId());
        log.info("Order canceled successfully: {}", command.getOrderId());
    }

    @KafkaHandler
    public void handleCompleteOrder(@Payload CompleteOrderCommand command) {
        orderService.completeOrder(command.getOrderId());
        log.info("Order completed successfully: {}", command.getOrderId());
    }
}
