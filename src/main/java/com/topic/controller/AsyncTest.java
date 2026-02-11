package com.topic.controller;

import com.topic.dto.api.response.MessageResponse;
import com.topic.service.KafkaLoggingService;
import com.topic.service.impl.AsyncTaskServiceImpl;
import com.topic.util.annotations.LoggingToSystemOut;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/async")
public class AsyncTest {

    private final KafkaLoggingService kafkaLoggingService;
    private final AsyncTaskServiceImpl asyncTaskService;

    public AsyncTest(
            KafkaLoggingService kafkaLoggingService,
            AsyncTaskServiceImpl asyncTaskService
    ) {
        this.kafkaLoggingService = kafkaLoggingService;
        this.asyncTaskService = asyncTaskService;
    }


    @GetMapping("/test")
    @LoggingToSystemOut
    public MessageResponse processAsyncTask() {

        asyncTaskService.delayInSeconds(5)
                .thenAccept(result -> {
                    kafkaLoggingService.makeLog("Task complete");
                    System.out.println("--> Async Task completed! :)");
                })
                .exceptionally(ex -> {
                    kafkaLoggingService.makeLog("Task interrupted");
                    return null;
                });

        return new MessageResponse("Ok. Task accepted");
    }

}
