package com.vendi.service;

import com.vendi.model.user.User;
import com.vendi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticatedService {

    @Autowired
    UserRepository userRepository;

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Usuário não autenticado");
        }

        var user = (User) authentication.getPrincipal();

        return userRepository.findById(user.getId()).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado no banco"));
    }
}
