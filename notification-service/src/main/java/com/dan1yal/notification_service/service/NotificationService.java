package com.dan1yal.notification_service.service;

import com.dan1yal.notification_service.command.SendNotificationCommand;

public interface NotificationService {
    void sendNotification(SendNotificationCommand command);
}
