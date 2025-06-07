package com.vendi.category.dto;

import com.vendi.category.model.Category;

import java.util.UUID;

public record CategoryResponseDTO(
        UUID category_id,
        String name,
        String description
) {
    public CategoryResponseDTO(Category category) {
        this(category.getId(), category.getName(), category.getDescription());
    }
}
