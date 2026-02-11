package com.topic.service.impl;

import com.topic.service.AsyncTaskService;
import com.topic.service.dto.AsyncTaskResult;
import com.topic.service.pools.AsyncTaskThreadPool;
import com.topic.util.annotations.LoggingToSystemOut;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncTaskServiceImpl implements AsyncTaskService {

    private final AsyncTaskThreadPool threadPool;

    public AsyncTaskServiceImpl(
            AsyncTaskThreadPool threadPool
    ) {
        this.threadPool = threadPool;
    }

    @Override
    @LoggingToSystemOut
    public CompletableFuture<AsyncTaskResult> delayInSeconds(int delay) {
        return threadPool.submit(() -> {
            try {
                Thread.sleep(delay * 1000L);
                return new AsyncTaskResult("Ok");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Task interrupted", e);
            }
        });
    }

}
