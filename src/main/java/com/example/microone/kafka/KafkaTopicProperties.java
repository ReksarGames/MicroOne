package com.example.microone.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.List;

@Setter
@Getter
@ConfigurationProperties(prefix = "spring.kafka.admin")
public class KafkaTopicProperties {
    private final List<Topic> topics;
    public KafkaTopicProperties(List<Topic> topics) {
        this.topics = topics;
    }

    public record Topic(String name, int partitions, int replicas) {
        @ConstructorBinding
        public Topic {
        }
    }
}