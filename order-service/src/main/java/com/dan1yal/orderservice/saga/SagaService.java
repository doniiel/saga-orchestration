package com.dan1yal.orderservice.saga;

import com.dan1yal.orderservice.command.ProcessPaymentCommand;
import com.dan1yal.orderservice.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Component
@KafkaListener(topics = {
        "${orders.event.topic.name}"
})
@RequiredArgsConstructor
public class SagaService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${orders.command.topic.name}")
    private String orderCommandTopicName;
    @Value("${inventory.command.topic.name}")
    private String inventoryCommandTopicName;


    @KafkaHandler
    public void handle(@Payload OrderCreatedEvent event) {
        BigDecimal totalCost = event.getPrice().multiply(BigDecimal.valueOf(event.getQuantity()));
        ProcessPaymentCommand command = ProcessPaymentCommand.builder()
                .orderId(event.getOrderId())
                .userId(event.getUserId())
                .amount(totalCost)
                .build();
        kafkaTemplate.send(inventoryCommandTopicName, command);
    }
}
