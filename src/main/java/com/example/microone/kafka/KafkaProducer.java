package com.example.microone.kafka;

import com.example.microone.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Message send(Message message) {
        Message messageResponse = new Message();
        String topic = "message";
        kafkaTemplate.send(topic, message);
        messageResponse.setText(message.getText());
        return messageResponse;
    }
}
