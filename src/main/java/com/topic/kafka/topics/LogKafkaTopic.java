package com.topic.kafka.topics;

import com.topic.kafka.KafkaTopic;
import com.topic.kafka.KafkaTopicInitializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@KafkaTopic(
        name = "logTopic",
        partitions = 1,
        replicationFactor = 1,
        retentionMs = "3600000" // 3600000 ms = 1 hour
)
@Component("Kafka.Topics.LogKafkaTopic")
public class LogKafkaTopic implements KafkaTopicInitializer {


    // todo: это еще один топик для теста - что спринг при запуске создает их два, и что они автовайрятся по именам
    //

    /*

    COPIED FROM DebugKafkaTopic.java

     */

    @Override
    public String getName() {
        return "logTopic";
    }

    @Override
    public NewTopic build() {
        return TopicBuilder
                .name(getName())
                .partitions(1)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, "3600000")
                .build();
    }
}
