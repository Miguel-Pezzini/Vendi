package com.vendi.cart.dto;

import com.vendi.cart.model.Cart;

import java.util.List;

public record CartResponseDTO(
        List<CartItemResponseDTO> items,
        int totalItems,
        float subtotal
) {
    public CartResponseDTO(Cart cart) {
        this(
                cart.getCartItems().stream().map(CartItemResponseDTO::new).toList(),
                cart.getCartItems().stream().mapToInt(item -> item.getQuantity()).sum(),
                (float) cart.getCartItems().stream()
                        .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                        .sum()
        );
    }
}
