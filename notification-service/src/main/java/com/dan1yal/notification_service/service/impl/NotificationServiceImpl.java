package com.dan1yal.notification_service.service.impl;

import com.dan1yal.notification_service.service.NotificationService;
import com.example.demo.commands.notification.SendNotificationCommand;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void sendNotification(SendNotificationCommand command) {

    }
}
