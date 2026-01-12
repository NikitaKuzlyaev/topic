package com.topic.kafka;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface KafkaTopic {
    String name();
    int partitions() default 1;
    int replicationFactor() default 1;
    String retentionMs() default "604800000"; // 604800000 ms = 7 days
}
