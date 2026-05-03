package com.topic.kafka.listeners;

import org.springframework.kafka.annotation.KafkaListener;


public class SimpleListener {

    @KafkaListener(topics = "logTopic", groupId = "group-1")
    public void listen(String message){
        System.out.println(message);
    }
}
