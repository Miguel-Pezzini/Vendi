package com.vendi.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserDTO(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String password) {
}
