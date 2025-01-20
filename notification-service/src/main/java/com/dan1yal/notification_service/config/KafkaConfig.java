package com.dan1yal.notification_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    private final static Integer TOPIC_REPLICATION_FACTOR = 3;
    private final static Integer TOPIC_PARTITIONS = 3;
    @Value("${notification.command.topic-name}")
    private String notificationCommandTopicName;
    @Value("${notification.event.topic-name}")
    private String notificationEventTopicName;

    @Bean
    NewTopic notificationCommandTopic() {
        return TopicBuilder.name(notificationCommandTopicName)
                .partitions(TOPIC_PARTITIONS)
                .replicas(TOPIC_REPLICATION_FACTOR)
                .build();
    }

    @Bean
    NewTopic notificationEventTopic() {
        return TopicBuilder.name(notificationEventTopicName)
                .partitions(TOPIC_PARTITIONS)
                .replicas(TOPIC_REPLICATION_FACTOR)
                .build();
    }
}
