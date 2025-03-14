package com.vendi.dto;

import com.vendi.model.user.UserRole;

public record RegisterUserDTO(String name, String email, String password, UserRole role) {
}