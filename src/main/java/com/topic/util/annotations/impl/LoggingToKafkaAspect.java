package com.topic.util.annotations.impl;

import com.topic.service.KafkaLoggingService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingToKafkaAspect {

    private final KafkaLoggingService kafkaLoggingService;

    public LoggingToKafkaAspect(
            KafkaLoggingService kafkaLoggingService
    ) {
        this.kafkaLoggingService = kafkaLoggingService;
    }


    @Around("@annotation(com.topic.util.annotations.LoggingToKafkaTopic)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        Object retVal = joinPoint.proceed();
        long endTime = System.nanoTime();

        String message = "Execute" + joinPoint.getSignature() + " in " + formatNanosToMs(endTime - startTime);
        kafkaLoggingService.makeLog(message);
        System.out.println(message);
        return retVal;
    }

    private String formatNanosToMs(long nanos) {
        double milliseconds = nanos / 1_000_000.0;
        return String.format("%.3f ms", milliseconds);
    }

}