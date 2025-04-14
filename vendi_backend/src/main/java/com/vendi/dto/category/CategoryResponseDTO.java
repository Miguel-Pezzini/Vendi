package com.vendi.dto.category;

import java.util.UUID;

public record CategoryResponseDTO(
        UUID category_id,
        String name,
        String description
) {
}
