package com.topic.kafka.topics;

import com.topic.kafka.KafkaTopic;
import com.topic.kafka.KafkaTopicInitializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@KafkaTopic(
        name = "debugTopic",
        partitions = 1,
        replicationFactor = 1,
        retentionMs = "3600000" // 3600000 ms = 1 hour
)
@Component
public class DebugKafkaTopic implements KafkaTopicInitializer {

    @Override
    public String getName() {
        return "debugTopic";
    }

    @Override
    public NewTopic build() {

        // todo: нехорошо, что одно и то же в аннтотации указано и здесь

        return TopicBuilder
                .name(getName())
                .partitions(1)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, "3600000")
                .build();
    }

}
