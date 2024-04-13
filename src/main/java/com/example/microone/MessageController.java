package com.example.microone;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/message-sender")
class MessageController {
    private final WebClient webClient;

    public MessageController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build(); // предполагаем, что второй сервис находится на порту 8080
    }

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        // Отправляем запрос на второй микросервис для получения сообщения
        Message response = webClient.post()
                .uri("/message-receiver/receive")
                .bodyValue(message)
                .retrieve()
                .bodyToMono(Message.class).block();
        return ResponseEntity.ok(response);
    }
}