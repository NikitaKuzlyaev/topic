package com.topic.kafka.service.impl;

import com.topic.kafka.service.KafkaProducerService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service("com.topic.kafka.service.KafkaProducerServiceImpl")
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topic, Object message) {
        kafkaTemplate.send(topic, message);
    }

    @Override
    public void send(String topic, String key, Object message) {
        kafkaTemplate.send(topic, key, message);
    }
}
