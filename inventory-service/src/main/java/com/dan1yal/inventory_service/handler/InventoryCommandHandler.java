package com.dan1yal.inventory_service.handler;

import com.dan1yal.inventory_service.command.ReserveInventoryCommand;
import com.dan1yal.inventory_service.event.InventoryReservationFailedEvent;
import com.dan1yal.inventory_service.event.InventoryReservedEvent;
import com.dan1yal.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = "${inventory.command.topic-name}", groupId = "${spring.kafka.consumer.group-id}")
public class InventoryCommandHandler {

    private final ProductService productService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${inventory.event.topic-name}")
    private String inventoryEventsTopicName;

    @KafkaHandler
    public void handleCommand(@Payload ReserveInventoryCommand command) {
        try {
            productService.reserveProduct(command.getProductId(), command.getQuantity());

            InventoryReservedEvent event = InventoryReservedEvent.builder()
                    .orderId(command.getOrderId())
                    .productId(command.getProductId())
                    .amount(command.getAmount())
                    .quantity(command.getQuantity())
                    .build();
            kafkaTemplate.send(inventoryEventsTopicName, command);
        }  catch (Exception e) {
            log.error("Error processing command", e);

            InventoryReservationFailedEvent event = InventoryReservationFailedEvent.builder()
                    .orderId(command.getOrderId())
                    .productId(command.getProductId())
                    .amount(command.getAmount())
                    .quantity(command.getQuantity())
                    .build();
            kafkaTemplate.send(inventoryEventsTopicName, event);
        }
    }
}
