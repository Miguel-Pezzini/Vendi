package com.vendi.wishlist.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddWishlistItemRequestDTO(
        @NotNull UUID productId
) {
}
