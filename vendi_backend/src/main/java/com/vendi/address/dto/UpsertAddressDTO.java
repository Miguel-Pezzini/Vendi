package com.vendi.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpsertAddressDTO(
        @NotBlank(message = "Street is required.")
        @Size(max = 255, message = "Street can have at most 255 characters.")
        String street,
        @NotBlank(message = "City is required.")
        @Size(max = 120, message = "City can have at most 120 characters.")
        String city,
        @NotBlank(message = "State is required.")
        @Size(max = 120, message = "State can have at most 120 characters.")
        String state,
        @NotBlank(message = "Zip code is required.")
        @Size(max = 20, message = "Zip code can have at most 20 characters.")
        String zipCode
) {
}
