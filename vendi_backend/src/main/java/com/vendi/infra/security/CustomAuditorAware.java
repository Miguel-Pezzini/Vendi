package com.vendi.infra.security;

import com.vendi.user.model.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Se o usuário estiver autenticado
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            User user = (User) authentication.getPrincipal();
            return Optional.of(user.getId().toString());  // Retorna o id de usuário
        }

        // Caso não esteja autenticado, retorna um valor default ou null
        return Optional.empty();
    }
}
