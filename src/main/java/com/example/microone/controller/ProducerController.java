package com.example.microone.controller;

import com.example.microone.dto.TransactionalDTO;
import com.example.microone.service.kafka.KafkaProducer;
import com.example.microone.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        log.info(messageResponse.getContent());
        return ResponseEntity.ok(messageResponse);
    }
    @PostMapping("/kafka/transfer")
    public ResponseEntity<String> transferToAnotherUser(@RequestBody TransactionalDTO transactionDTO) {
        try {
            kafkaProducer.sendTransactional(transactionDTO)
                    .thenAccept(sendMessage -> {
                        log.info("Transactional sent");
                    }).exceptionally(ex -> {
                        log.error("Transactional failed", ex.getCause());
                        return null; // или обработать иначе
                    });
            return ResponseEntity.status(HttpStatus.OK).body("Transaction sent.");
        } catch (Exception e) {
            log.error("Failed to send transaction through Kafka", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send transaction.");
        }
    }
}
