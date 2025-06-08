package com.vendi.user.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public record RegisterResponseDTO(String token, String roles) {
}
