package com.example.microone.controller;

import com.example.microone.dto.TransactionalDTO;
import com.example.microone.jpa.UserRepository;
import com.example.microone.model.User;
import com.example.microone.service.TransactionalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Transactional")
@Slf4j
public class TransactionalController {
    private final TransactionalService transactionalService;
    private final UserRepository userRepository;

    @Autowired
    public TransactionalController(TransactionalService transactionalService, UserRepository userRepository) {
        this.transactionalService = transactionalService;
        this.userRepository = userRepository;
    }

    @PostMapping("/transferToAnotherUser")
    public ResponseEntity<String> transferToAnotherUser(@RequestBody TransactionalDTO requestDTO) {
        Optional<User> currentUser = userRepository.findByUuid(requestDTO.getCurrentUserId());
        Optional<User> targetUser = userRepository.findByUuid(requestDTO.getTargetUserId());
        try {
            transactionalService.transferMoney(currentUser, targetUser, requestDTO.getAmount());
            return ResponseEntity.ok("Transaction successful.");
        } catch (IllegalArgumentException e) {
            log.error("Invalid transaction request", e);
            return ResponseEntity.badRequest().body("Transaction failed: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error during transaction", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Transaction failed due to an internal error.");
        }
    }

}
