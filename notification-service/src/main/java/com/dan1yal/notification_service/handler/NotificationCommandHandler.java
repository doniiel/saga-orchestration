package com.dan1yal.notification_service.handler;

import com.dan1yal.notification_service.service.NotificationService;
import com.dan1yal.notification_service.util.MessageUtil;
import com.example.demo.commands.notification.SendNotificationCommand;
import com.example.demo.events.notification.NotificationFailedSentEvent;
import com.example.demo.events.notification.NotificationSentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;

@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = "${notification.command.topic-name}", groupId = "${spring.kafka.consumer.group-id}")
public class NotificationCommandHandler {

    private final NotificationService notificationService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${notification.event.topic-name}")
    private String notificationEventTopicName;

    @KafkaHandler
    public void handleCommand(@Payload SendNotificationCommand command) {
        try {
            notificationService.sendNotification(command);
            processSuccessfulNotification(command);
            log.info("Notification sent successfully for Order ID: {}", command.getOrderId());
        } catch (Exception e) {
            processFailedNotification(command);
            log.error("Error sending notification for Order ID: {}", command.getOrderId(), e);
        }
    }

    private void processSuccessfulNotification(SendNotificationCommand command) {
        NotificationSentEvent event = new NotificationSentEvent(
                command.getOrderId(),
                MessageUtil.SUCCESS_MESSAGE,
                command.getProductId(),
                command.getQuantity(),
                command.getAmount()
        );
        kafkaTemplate.send(notificationEventTopicName, event);
    }

    private void processFailedNotification(SendNotificationCommand command) {
        NotificationFailedSentEvent event = new NotificationFailedSentEvent(
                command.getOrderId(),
                MessageUtil.ERROR_MESSAGE
        );
        kafkaTemplate.send(notificationEventTopicName, event);
    }
}
