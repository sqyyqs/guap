package com.sqy.guap.service;

import com.sqy.guap.domain.User;
import com.sqy.guap.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getByUsername(String username) {
        return userRepository.getByUsername(username.replaceAll("_", " "));
    }
}
