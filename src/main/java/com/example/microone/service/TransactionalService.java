package com.example.microone.service;

import com.example.microone.jpa.TransactionalRepository;
import com.example.microone.jpa.UserRepository;
import com.example.microone.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionalService {
    private final TransactionalRepository transactionalRepository;
    private final UserRepository userRepository;

    public TransactionalService(TransactionalRepository transactionalRepository, UserRepository userRepository) {
        this.transactionalRepository = transactionalRepository;
        this.userRepository = userRepository;
    }

    public void addBalanceToUser(User userRequest, Double amount) {
        Optional<User> userLocal = userRepository.findByUsername(userRequest.getUsername());
        if (userLocal.isPresent()) {
            UUID userId = userLocal.get().getUuid();
            userRepository.addBalance(userId, amount);
        }
    }

    @Transactional
    public void transferMoney(Optional<User> fromUserOpt, Optional<User> toUserOpt, double amount) throws IllegalArgumentException {
        User fromUser = fromUserOpt.orElseThrow(() -> new IllegalArgumentException("Sender cannot be null"));
        User toUser = toUserOpt.orElseThrow(() -> new IllegalArgumentException("Receiver cannot be null"));

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (fromUser.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        fromUser.setBalance(fromUser.getBalance() - amount);
        toUser.setBalance(toUser.getBalance() + amount);

        userRepository.save(fromUser);
        userRepository.save(toUser);
    }
}
