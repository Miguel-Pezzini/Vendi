package com.vendi.category.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CategoryRequestDTO(
        @NotNull String name,
        @NotNull String description,
        UUID father_category_id) {
}
