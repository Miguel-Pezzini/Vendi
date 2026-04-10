package com.vendi.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateMeDTO(
        @NotBlank(message = "Name is required.")
        @Size(max = 120, message = "Name can have at most 120 characters.")
        String name,

        @NotBlank(message = "Email is required.")
        @Email(message = "Email must be valid.")
        @Size(max = 255, message = "Email can have at most 255 characters.")
        String email
) {
}
