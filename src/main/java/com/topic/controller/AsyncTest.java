package com.topic.controller;

import com.topic.dto.api.response.MessageResponse;
import com.topic.kafka.KafkaTopicInitializer;
import com.topic.util.annotations.LoggingToSystemOut;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/async")
public class AsyncTest {

    private final KafkaTopicInitializer debugKafkaTopic;

    private final KafkaTopicInitializer logKafkaTopic;

    public AsyncTest(
            @Qualifier("Kafka.Topics.DebugKafkaTopic")
            KafkaTopicInitializer debugKafkaTopic,

            @Qualifier("Kafka.Topics.LogKafkaTopic")
            KafkaTopicInitializer logKafkaTopic
    ) {
        this.debugKafkaTopic = debugKafkaTopic;
        this.logKafkaTopic = logKafkaTopic;
    }


    @GetMapping("/test")
    @LoggingToSystemOut
    public MessageResponse processAsyncTask(){
        return new MessageResponse("Fail"); // заглушка
    }

}
