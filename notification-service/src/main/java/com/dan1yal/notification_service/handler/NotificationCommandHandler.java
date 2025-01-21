package com.dan1yal.notification_service.handler;

import com.dan1yal.notification_service.command.SendNotificationCommand;
import com.dan1yal.notification_service.event.NotificationFailedSentEvent;
import com.dan1yal.notification_service.event.NotificationSentEvent;
import com.dan1yal.notification_service.service.NotificationService;
import com.dan1yal.notification_service.util.MessageUtil;
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
            processSuccessfulNotification(command);
            notificationService.sendNotification(command);
            log.info("Notification sent successfully for Order ID: {}", command.getOrderId());
        } catch (Exception e) {
            processFailedNotification(command);
            log.error("Error sending notification for Order ID: {}", command.getOrderId(), e);
        }
    }

    private void processSuccessfulNotification(SendNotificationCommand command) {
        NotificationSentEvent event = NotificationSentEvent.builder()
                .orderId(command.getOrderId())
                .message(MessageUtil.SUCCESS_MESSAGE)
                .build();
        kafkaTemplate.send(notificationEventTopicName, event);
    }

    private void processFailedNotification(SendNotificationCommand command) {
        NotificationFailedSentEvent event = NotificationFailedSentEvent.builder()
                .orderId(command.getOrderId())
                .message(MessageUtil.ERROR_MESSAGE)
                .build();
        kafkaTemplate.send(notificationEventTopicName, event);
    }
}
