package com.topic.scheduler.quartz.jobs;

import com.topic.kafka.service.KafkaProducerService;
import com.topic.kafka.topics.DebugKafkaTopic;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class DestroyKafkaTopicJob implements Job {

    @Autowired
    @Qualifier("com.topic.kafka.service.KafkaProducerServiceImpl")
    private KafkaProducerService kafkaProducerService;

    @Autowired
    @Qualifier("Kafka.Topics.DebugKafkaTopic")
    private DebugKafkaTopic debugKafkaTopic;

    public DestroyKafkaTopicJob() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Теперь @Autowired должен работать
        kafkaProducerService.send(debugKafkaTopic.getName(), "aboba");
    }
}