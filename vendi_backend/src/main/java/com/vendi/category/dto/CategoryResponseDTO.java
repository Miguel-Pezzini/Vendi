package com.vendi.category.dto;

import com.vendi.category.model.Category;

import java.util.UUID;

public record CategoryResponseDTO(
        UUID id,
        UUID fatherCategoryId,
        String name,
        String description
) {
    public CategoryResponseDTO(Category category) {
        this(
                category.getId(),
                category.getCategoryFather() != null ? category.getCategoryFather().getId() : null,
                category.getName(),
                category.getDescription());
    }
}
