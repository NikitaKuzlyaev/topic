package com.topic.kafka.service;

public interface KafkaProducerService {

    void send(String topic, Object message);

    void send(String topic,String key,  Object message);
}
