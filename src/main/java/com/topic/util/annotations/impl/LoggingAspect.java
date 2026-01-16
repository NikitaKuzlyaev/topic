package com.topic.util.annotations.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    @Around("@annotation(com.topic.util.annotations.Logging)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {


        System.out.println(">>>  Started: " + joinPoint.getSignature());
        long startTime = System.nanoTime();

        Object retVal = joinPoint.proceed();

        long endTime = System.nanoTime();
        System.out.println("<<<  Finished: " + joinPoint.getSignature() + " in " + formatNanosToMs(endTime - startTime));

        return retVal;
    }

    private String formatNanosToMs(long nanos) {
        double milliseconds = nanos / 1_000_000.0;
        return String.format("%.3f ms", milliseconds);
    }
}