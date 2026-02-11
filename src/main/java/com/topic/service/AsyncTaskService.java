package com.topic.service;

import com.topic.service.dto.AsyncTaskResult;

import java.util.concurrent.CompletableFuture;

public interface AsyncTaskService {

    CompletableFuture<AsyncTaskResult> delayInSeconds(int delay);

}
