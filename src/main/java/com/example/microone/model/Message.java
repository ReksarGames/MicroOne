package com.example.microone.model;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Message")
public class Message extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "user_id") // Я правильно понял что senderId - это UserId?
    private User sender;

    @Column(name = "content") // Текст который мы отправляем
    private String content;

    @Column(name = "is_read")
    private Boolean isRead;
}
