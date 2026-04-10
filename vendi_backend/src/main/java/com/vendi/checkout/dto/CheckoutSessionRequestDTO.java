package com.vendi.checkout.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CheckoutSessionRequestDTO(
        @NotBlank(message = "First name is required.")
        String firstName,
        String companyName,
        @NotBlank(message = "Address is required.")
        String address,
        String additionalAddress,
        @NotBlank(message = "City is required.")
        String city,
        @NotBlank(message = "Phone is required.")
        String phone,
        @NotBlank(message = "Email is required.")
        @Email(message = "Email must be valid.")
        String email
) {
}
