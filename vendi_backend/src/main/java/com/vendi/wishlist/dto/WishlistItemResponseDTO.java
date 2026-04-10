package com.vendi.wishlist.dto;

import com.vendi.product.dto.ProductDTO;
import com.vendi.wishlist.model.WishlistItem;

import java.util.UUID;

public record WishlistItemResponseDTO(
        UUID id,
        ProductDTO product
) {
    public WishlistItemResponseDTO(WishlistItem wishlistItem) {
        this(
                wishlistItem.getId(),
                new ProductDTO(wishlistItem.getProduct())
        );
    }
}
