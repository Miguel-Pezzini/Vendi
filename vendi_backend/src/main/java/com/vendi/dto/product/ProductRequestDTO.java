package com.vendi.dto.product;

public record ProductRequestDTO(
        int limit,
        boolean recent
) {
}
