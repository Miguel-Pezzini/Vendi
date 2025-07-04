package com.vendi.auth.service;

import com.vendi.infra.security.TokenService;
import com.vendi.shared.exception.ResourceAlreadyExistsException;
import com.vendi.user.dto.RegisterResponseDTO;
import com.vendi.user.dto.RegisterUserDTO;
import com.vendi.user.model.User;
import com.vendi.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    public RegisterResponseDTO register(RegisterUserDTO body) throws ResourceAlreadyExistsException {
        if(this.repository.findByEmail(body.email()) != null) throw new ResourceAlreadyExistsException("This email is already registered");

        String encryptedPassword = new BCryptPasswordEncoder().encode(body.password());
        User newUser = new User(body.email(), encryptedPassword, body.name(), body.role());

        this.repository.save(newUser);

        var token = tokenService.generateToken(newUser);

        return new RegisterResponseDTO(token, newUser.getAuthorities().toString());
    }
}
