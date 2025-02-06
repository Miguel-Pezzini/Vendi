package com.vendi.controller;

import com.vendi.domain.user.*;
import com.vendi.infra.security.TokenService;
import com.vendi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(body.email(), body.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterUserDTO body) {
        if(this.repository.findByEmail(body.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(body.password());
        User newUser = new User(body.email(), encryptedPassword, body.role());

        this.repository.save(newUser);

        var token = tokenService.generateToken(newUser);

        return ResponseEntity.ok(new RegisterResponseDTO(token));


    }
}
