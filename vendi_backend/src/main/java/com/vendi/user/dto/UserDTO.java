package com.vendi.user.dto;

import com.vendi.address.dto.AddressDTO;
import com.vendi.user.model.User;

import java.util.UUID;

public record UserDTO(UUID id, String name, String email, AddressDTO currentAddress, String role) {
    public UserDTO(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCurrentAddress() != null ? new AddressDTO(user.getCurrentAddress()) : null,
                user.getRole().name()
        );
    }
}
