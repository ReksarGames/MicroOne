package com.example.microone.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.List;

@Slf4j
@Configuration
public class KafkaConfiguration {
    private final KafkaTopicProperties kafkaTopicProperties;

    public KafkaConfiguration(KafkaTopicProperties kafkaTopicProperties) {
        this.kafkaTopicProperties = kafkaTopicProperties;
    }

    @Bean
    public List<NewTopic> createTopicList(){
        log.info("Starting to createTopic Kafka on the configuration properties");

        List<NewTopic> topics = kafkaTopicProperties.getTopics().stream()
                .map(topic -> {
                    log.info("Creating topic with name: {}, partitions: {}, replicas: {}",
                            topic.name(), topic.partitions(), topic.replicas());
                    return TopicBuilder.name(topic.name())
                        .partitions(topic.partitions())
                        .replicas((short) topic.replicas())
                        .build();
                })
                .toList();
        log.info("Successfully created {} topics", topics);
        return topics;
    }
}
