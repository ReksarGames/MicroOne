package com.example.microone.service;

import com.example.microone.jpa.UserRepository;
import com.example.microone.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String username, Double amount) {
        User user = User.builder()
                .username(username)
                .balance(amount)
                .build();
        userRepository.save(user);
    }
}
