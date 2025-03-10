package com.vendi.services;

import com.vendi.domain.user.RegisterUserDTO;
import com.vendi.domain.user.User;
import com.vendi.repository.UserRepository;
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
