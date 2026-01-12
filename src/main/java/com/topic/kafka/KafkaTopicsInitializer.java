package com.topic.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

// todo: имя похоже на имя интерфейса в этом каталоге, но сейчас не хочу придумывать другое
@Component
public class KafkaTopicsInitializer implements ApplicationRunner {

    private final KafkaAdmin kafkaAdmin;
    private final ApplicationContext applicationContext;

    public KafkaTopicsInitializer(
            KafkaAdmin kafkaAdmin,
            ApplicationContext applicationContext
    ) {
        this.kafkaAdmin = kafkaAdmin;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, KafkaTopicInitializer> initializers = applicationContext.getBeansOfType(KafkaTopicInitializer.class);

        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())){

            Set<String> topics = adminClient.listTopics().names().get();

            for (KafkaTopicInitializer initializer : initializers.values()){
                String topicName = initializer.getName();

                if (!topics.contains(topicName)){
                    NewTopic topic = initializer.build();
                    adminClient.createTopics(List.of(topic)).all().get();
                    initializer.callback();
                }
            }

        } catch (Exception e){
            throw new RuntimeException("Fail in KafkaTopicsInitializer");
        }
    }
}
