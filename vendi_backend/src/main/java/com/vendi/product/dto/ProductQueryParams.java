package com.vendi.product.dto;

import java.util.UUID;

public record ProductQueryParams(
        Integer limit,
        Integer page,
        Integer size,
        String search,
        UUID categoryId
) {
    public static final int DEFAULT_SIZE = 20;
    public static final int MAX_SIZE = 100;

    public int resolvedPage() {
        return page == null || page < 0 ? 0 : page;
    }

    public int resolvedSize() {
        int candidate = limit != null ? limit : (size != null ? size : DEFAULT_SIZE);
        if (candidate < 1) {
            return DEFAULT_SIZE;
        }
        return Math.min(candidate, MAX_SIZE);
    }
}
