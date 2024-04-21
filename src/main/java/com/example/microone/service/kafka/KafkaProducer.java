package com.example.microone.service.kafka;

import com.example.microone.dto.TransactionalDTO;
import com.example.microone.model.Message;
import com.example.microone.service.TransactionalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final TransactionalService transactionalService;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate, TransactionalService transactionalService) {
        this.kafkaTemplate = kafkaTemplate;
        this.transactionalService = transactionalService;
    }

    public Message send(Message message) {
        Message messageResponse = new Message();
        String topic = "message";
        kafkaTemplate.send(topic, message);
        messageResponse.setContent(message.getContent());
        return messageResponse;
    }
    public CompletableFuture<SendResult<String, Object>> sendTransactional(TransactionalDTO request) {
        String topic = "transaction";
        String key = String.valueOf(request.getId());

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
