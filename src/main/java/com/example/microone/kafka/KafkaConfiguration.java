package com.example.microone.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class KafkaConfiguration {
    @Value("${spring.kafka.admin.bootstrap-servers}")
    private String bootstrapServers;
    @Bean
    public NewTopic newTopic(){
        try {
            log.info("Creating a new topic 'message' with 1 partition and replication factor 1.");
            return new NewTopic("message", 1, (short) 1);
        } catch (Exception e) {
            log.error("Failed to create new topic 'message': " + e.getMessage());
            return null; // или обработать иначе
        }    }
}
