package com.vendi.domain.user;

public record RegisterUserDTO(String name, String email, String password, UserRole role) {
}