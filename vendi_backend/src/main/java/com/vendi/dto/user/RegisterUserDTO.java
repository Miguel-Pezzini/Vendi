package com.vendi.dto.user;

import com.vendi.model.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegisterUserDTO(
        @NotNull String name,
        @NotNull @Email String email,
        @NotNull String password,
        @NotNull UserRole role) {
}