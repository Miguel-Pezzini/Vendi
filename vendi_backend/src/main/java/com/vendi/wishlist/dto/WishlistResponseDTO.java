package com.vendi.wishlist.dto;

import com.vendi.wishlist.model.Wishlist;

import java.util.List;

public record WishlistResponseDTO(
        List<WishlistItemResponseDTO> items,
        int totalItems
) {
    public WishlistResponseDTO(Wishlist wishlist) {
        this(
                wishlist.getWishlistItems().stream()
                        .map(WishlistItemResponseDTO::new)
                        .toList(),
                wishlist.getWishlistItems().size()
        );
    }
}
