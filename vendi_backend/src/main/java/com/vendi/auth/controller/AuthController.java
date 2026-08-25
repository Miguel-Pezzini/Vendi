package com.vendi.auth.controller;

import com.vendi.auth.service.AuthService;
import com.vendi.shared.exception.ResourceAlreadyExistsException;
import com.vendi.user.dto.LoginRequestDTO;
import com.vendi.user.dto.LoginResponseDTO;
import com.vendi.user.dto.RegisterResponseDTO;
import com.vendi.user.dto.RegisterUserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO body) {
        return ResponseEntity.ok(authService.login(body));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterUserDTO body) throws ResourceAlreadyExistsException {
        return ResponseEntity.ok(authService.register(body));
    }
}
