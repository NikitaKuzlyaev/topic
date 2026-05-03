package com.topic.controller.test.kafka;


import com.topic.dto.api.response.MessageResponse;
import com.topic.service.KafkaLoggingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/kafka")
public class Sendler {

    private final KafkaLoggingService kafkaLoggingService;

    public Sendler(
            @Qualifier("KafkaNPartitionsServiceImpl")
            KafkaLoggingService kafkaLoggingService
    ) {
        this.kafkaLoggingService = kafkaLoggingService;
    }

    @PostMapping("/send")
    public MessageResponse sendToKafka() {
        kafkaLoggingService.makeLog("aboba");
        return new MessageResponse("all right");
    }

}
