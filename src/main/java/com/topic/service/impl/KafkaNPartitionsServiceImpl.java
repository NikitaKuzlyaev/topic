package com.topic.service.impl;

import com.topic.kafka.service.KafkaProducerService;
import com.topic.kafka.topics.LogKafkaTopic;
import com.topic.service.KafkaLoggingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service("KafkaNPartitionsServiceImpl")
public class KafkaNPartitionsServiceImpl implements KafkaLoggingService {

    private final KafkaProducerService kafkaProducerService;
    private final LogKafkaTopic kafkaTopic;

    public KafkaNPartitionsServiceImpl(
            @Qualifier("com.topic.kafka.service.KafkaProducerServiceImpl")
            KafkaProducerService kafkaProducerService,

            @Qualifier("Kafka.Topics.LogKafkaTopic")
            LogKafkaTopic kafkaTopic
    ) {
        this.kafkaProducerService = kafkaProducerService;
        this.kafkaTopic = kafkaTopic;
    }

    @Override
    public void makeLog(String message) {
        kafkaProducerService.send(kafkaTopic.getName(), String.valueOf(ThreadLocalRandom.current().nextInt() % 2), message);
    }

}
