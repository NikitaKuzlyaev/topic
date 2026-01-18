package com.topic.config;

import com.topic.scheduler.quartz.jobs.DestroyKafkaTopicJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzJobsConfig {

    // todo. я понимаю, что это неправильное решение - просто хотелось быстро проверить, что оно работатет

    @Bean
    public JobDetail destroyTopicJobDetail() {
        return JobBuilder.newJob(DestroyKafkaTopicJob.class)
                .withIdentity("destroyTopicJob", "kafkaJobs")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger destroyTopicTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(destroyTopicJobDetail())
                .withIdentity("destroyTopicTrigger", "kafkaJobs")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever())
                .build();
    }

}