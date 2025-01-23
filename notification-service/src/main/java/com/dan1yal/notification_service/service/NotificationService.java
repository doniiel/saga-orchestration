package com.dan1yal.notification_service.service;


import com.example.demo.commands.notification.SendNotificationCommand;

public interface NotificationService {
    void sendNotification(SendNotificationCommand command);
}
