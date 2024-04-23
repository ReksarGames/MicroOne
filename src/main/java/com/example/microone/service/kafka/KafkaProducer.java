package com.example.microone.service.kafka;

import com.example.microone.dto.MessageDTO;
import com.example.microone.dto.TransactionalDTO;
import com.example.microone.jpa.UserRepository;
import com.example.microone.model.Message;
import com.example.microone.model.User;
import com.example.microone.service.TransactionalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final TransactionalService transactionalService;
    private final UserRepository userRepository;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate, TransactionalService transactionalService, UserRepository userRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.transactionalService = transactionalService;
        this.userRepository = userRepository;
    }

    public MessageDTO send(MessageDTO messageRequest) {
        Message messageResponse = new Message();
        String topic = "message";
        kafkaTemplate.send(topic, messageRequest);
        messageResponse.setContent(messageRequest.getContent());
        return messageRequest;
    }

    public CompletableFuture<SendResult<String, Object>> sendTransactional(@NotNull TransactionalDTO request) {
        String topic = "transaction";
        String key = String.valueOf(request.getId());

        log.info("Attempting to transfer money to another User by transferMethod by TransactionalService");
        try {
            Optional<User> currentUser = userRepository.findByUuid(request.getCurrentUserId());
            Optional<User> targetUser = userRepository.findByUuid(request.getTargetUserId());
            transactionalService.transferMoney(currentUser, targetUser, request.getAmount());
            log.info("Money transfer successful");
        } catch (Exception e){
            log.error("Failed to transfer money", e);
            throw new RuntimeException("Failed to transfer money", e);
        }

        // Логирование перед отправкой
        log.info("Sending transactional data to Kafka topic: " + topic);
        // Отправляем сообщение с ключом для улучшения консистенции разделения
        return kafkaTemplate.send(new ProducerRecord<>(topic, key, request))
                .handle((result, ex) -> {
                    if (ex != null) {
                        // Логирование ошибок отправки
                        log.error("Failed to send transactional data: " + ex.getMessage());
                        throw new RuntimeException("Failed to send data to Kafka", ex);
                    }
                    // Логирование успешной отправки
                    RecordMetadata metadata = result.getRecordMetadata();
                    log.trace("Message sent successfully to topic " + metadata.topic() +
                            " partition " + metadata.partition() +
                            " at offset " + metadata.offset());
                    return result;
                });
    }
}
