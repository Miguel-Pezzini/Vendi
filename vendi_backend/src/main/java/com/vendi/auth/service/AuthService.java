package com.vendi.auth.service;

import com.vendi.infra.security.TokenService;
import com.vendi.shared.exception.ResourceAlreadyExistsException;
import com.vendi.user.dto.LoginRequestDTO;
import com.vendi.user.dto.LoginResponseDTO;
import com.vendi.user.dto.RegisterResponseDTO;
import com.vendi.user.dto.RegisterUserDTO;
import com.vendi.user.model.User;
import com.vendi.user.model.UserRole;
import com.vendi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterResponseDTO register(RegisterUserDTO body) throws ResourceAlreadyExistsException {
        if (this.repository.findByEmail(body.email()) != null) {
            throw new ResourceAlreadyExistsException("This email is already registered");
        }

        User newUser = new User(body.email(), passwordEncoder.encode(body.password()), body.name(), UserRole.USER);
        this.repository.save(newUser);

        var token = tokenService.generateToken(newUser);
        return new RegisterResponseDTO(token, newUser.getAuthorities().toString());
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return new LoginResponseDTO(token, auth.getAuthorities().toString());
    }
}
