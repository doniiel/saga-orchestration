package com.dan1yal.inventory_service.handler;

import com.example.demo.commands.inventory.CancelReserveInventoryCommand;
import com.example.demo.events.inventory.InventoryReservationFailedEvent;
import com.example.demo.events.inventory.InventoryReservedEvent;
import com.dan1yal.inventory_service.service.ProductService;
import com.example.demo.commands.inventory.ReserveInventoryCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = "${inventory.command.topic-name}")
@Transactional
public class InventoryCommandHandler {

    private final ProductService productService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${inventory.event.topic-name}")
    private String inventoryEventsTopicName;

    @KafkaHandler
    public void handleCommand(@Payload ReserveInventoryCommand command) {
        log.info("Received ReserveInventoryCommand: {}", command);
        try {
            productService.reserveProduct(command.getProductId(), command.getQuantity());
            processSuccessfulReservation(command);
            log.info("Inventory successfully reserved for Order ID: {}", command.getOrderId());
        }  catch (Exception e) {
            log.error("Error processing ReserveInventoryCommand: {}", command, e);
            processFailedReservation(command, e);
        }
    }

    @KafkaHandler
    public void handleCommand(@Payload CancelReserveInventoryCommand command) {
            productService.cancelReservation(command.getProductId(), command.getQuantity());
            log.info("Inventory successfully canceled for Order ID: {}", command.getOrderId());
            processFailedReservation(command, null);
    }

    private void processSuccessfulReservation(ReserveInventoryCommand command) {
        InventoryReservedEvent event = new InventoryReservedEvent(
                command.getOrderId(),
                command.getProductId(),
                command.getQuantity(),
                command.getAmount()
        );
        kafkaTemplate.send(inventoryEventsTopicName, event);
    }
    private void processFailedReservation(ReserveInventoryCommand command, Exception e) {
        InventoryReservationFailedEvent event = new InventoryReservationFailedEvent(
                command.getOrderId(),
                command.getProductId(),
                command.getQuantity(),
                command.getAmount()
        );
        kafkaTemplate.send(inventoryEventsTopicName, event);
    }

    private void processFailedReservation(CancelReserveInventoryCommand command, Exception e) {
        InventoryReservationFailedEvent event = new InventoryReservationFailedEvent(
                command.getOrderId(),
                command.getProductId(),
                command.getQuantity(),
                command.getAmount()
        );
        kafkaTemplate.send(inventoryEventsTopicName, event);
    }
}
