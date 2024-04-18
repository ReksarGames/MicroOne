package com.example.microone.controller;

import com.example.microone.kafka.KafkaProducer;
import com.example.microone.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafkaController")
@Slf4j
public class ProducerController {
    private final KafkaProducer kafkaProducer;

    @Autowired
    public ProducerController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/kafka/send")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        Message messageResponse = kafkaProducer.send(message);
        log.info(messageResponse.getText());
        return ResponseEntity.ok(messageResponse);
    }
}
