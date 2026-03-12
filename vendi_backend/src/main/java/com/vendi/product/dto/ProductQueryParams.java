package com.vendi.product.dto;

import java.util.UUID;

public record ProductQueryParams(
        Integer limit,
        String search,
        UUID categoryId
) {
}
