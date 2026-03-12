package com.vendi.cart.dto;

import com.vendi.cart.model.CartItem;
import com.vendi.product.dto.ProductDTO;

import java.util.UUID;

public record CartItemResponseDTO(
        UUID id,
        int quantity,
        float subtotal,
        ProductDTO product
) {
    public CartItemResponseDTO(CartItem cartItem) {
        this(
                cartItem.getId(),
                cartItem.getQuantity(),
                cartItem.getProduct().getPrice() * cartItem.getQuantity(),
                new ProductDTO(cartItem.getProduct())
        );
    }
}
