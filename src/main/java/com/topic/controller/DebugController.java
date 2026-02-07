package com.topic.controller;

import com.topic.util.annotations.LoggingToSystemOut;
import com.topic.util.exeptions.NotImplementedException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugController {

    private final KafkaAdmin kafkaAdmin;
    private final StringRedisTemplate redisTemplate;

    public DebugController(
            KafkaAdmin kafkaAdmin,
            StringRedisTemplate redisTemplate
    ) {
        this.kafkaAdmin = kafkaAdmin;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello!";
    }

    @GetMapping("/test-kafka")
    @LoggingToSystemOut
    public String testKafka() {
        try {
            kafkaAdmin.describeTopics();
            return "Kafka подключена!";
        } catch (Exception e) {
            return "Ошибка подключения: " + e.getMessage();
        }
    }

    @GetMapping("/test-redis")
    @LoggingToSystemOut
    public String testRedis() {
        try {
            redisTemplate.opsForValue().set("key", "value");
            String value = redisTemplate.opsForValue().get("key");
            return "Redis работает!\n" + "value = " + value;
        } catch (Exception e) {
            return "Ошибка подключения: " + e.getMessage();
        }
    }

    @PostMapping("/kafka")
    public String sendMessageToKafka() {
        throw new NotImplementedException("");
    }

}
