package com.example.microone;

import com.example.microone.kafka.KafkaTopicProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Slf4j
@EnableConfigurationProperties(KafkaTopicProperties.class)
public class MicroOneApplication implements Runnable{
    public static void main(String[] args) {
        SpringApplication.run(MicroOneApplication.class, args);
    }
    @Override
    public void run() {
        log.info("http://localhost:9000/h2-console/");
    }
}
