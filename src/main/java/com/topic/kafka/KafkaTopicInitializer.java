package com.topic.kafka;

import org.apache.kafka.clients.admin.NewTopic;

public interface KafkaTopicInitializer {
    String getName();
    NewTopic build();
    default void callback() {}; // todo: наверно, не самое удачное имя
}
