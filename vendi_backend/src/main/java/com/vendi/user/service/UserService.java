package com.vendi.user.service;

import com.vendi.user.dto.RegisterUserDTO;
import com.vendi.user.model.User;
import com.vendi.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    public Optional<User> findUser(UUID userId) {
        return repository.findById(userId);
    }

    public User save(RegisterUserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());

        return repository.save(user);
    }
}
