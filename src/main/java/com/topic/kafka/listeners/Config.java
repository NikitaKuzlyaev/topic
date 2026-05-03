package com.topic.kafka.listeners;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean(name = "sss")
    public SimpleListener someMethod(){
        return new SimpleListener();
    }

}
