package com.dan1yal.orderservice.handler;

import com.dan1yal.orderservice.command.order.CancelOrderCommand;
import com.dan1yal.orderservice.event.order.OrderCompletedEvent;
import com.dan1yal.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;

@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = {"${order.command.topic-name}"}, groupId = "${spring.kafka.consumer.group-id}")
public class OrderCommandHandler {

    private final OrderService orderService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${order.event.topic-name}")
    private String orderEventTopicName;

//    @KafkaHandler
//    public void handleCommand(CompleteOrderCommand command) {
//        orderService.completeOrder(command.getOrderId());
//        OrderCompletedEvent event = OrderCompletedEvent.builder().orderId(command.getOrderId()).build();
//        kafkaTemplate.send(orderEventTopicName, event);
//    }

    @KafkaHandler
    public void handleCancelOrder(@Payload CancelOrderCommand command) {
        orderService.cancelOrder(command.getOrderId());
        log.info("Order canceled successfully: {}", command.getOrderId());
    }
}
