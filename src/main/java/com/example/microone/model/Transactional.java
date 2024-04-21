package com.example.microone.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Transactional")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Transactional extends AbstractEntity {
    @Column(name = "amount", nullable = false)
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
