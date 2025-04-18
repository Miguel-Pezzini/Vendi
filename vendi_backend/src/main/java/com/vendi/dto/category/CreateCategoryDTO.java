package com.vendi.dto.category;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateCategoryDTO(
        @NotNull String name,
        @NotNull String description,
        UUID category_father_id) {
}
