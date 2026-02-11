package com.topic.service.pools;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ConfigurationProperties(prefix = "thread.pool.asynctask")
@Getter
@Setter
public class AsyncTaskThreadPool {

    // стандартные значения, которые будут использоваться, если не найдутся настройки в конфиге
    private int corePoolSize = 4;
    private int maxPoolSize = 8;
    private int keepAliveTime = 10;
    private int queueCapacity = 1000;

    private final AtomicInteger counter = new AtomicInteger(1);

    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(queueCapacity);


    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            TimeUnit.SECONDS,
            queue,

            new ThreadFactory() {
                private final AtomicInteger counter = new AtomicInteger(1);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "async-task-" + counter.getAndIncrement());
                }
            },

            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public AsyncTaskThreadPool() {
    }

    public CompletableFuture<Void> submit(Runnable task) {
        return CompletableFuture.runAsync(task, executor);
    }

    public <T> CompletableFuture<T> submit(Callable<T> task) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return task.call();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }, executor);
    }

    public void shutdown() {
        executor.shutdown();
    }

}
