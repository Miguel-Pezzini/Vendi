package com.vendi.cart.dto;

import com.vendi.cart.model.Cart;
import com.vendi.shared.money.Money;

import java.math.BigDecimal;
import java.util.List;

public record CartResponseDTO(
        List<CartItemResponseDTO> items,
        int totalItems,
        BigDecimal subtotal
) {
    public CartResponseDTO(Cart cart) {
        this(
                cart.getCartItems().stream().map(CartItemResponseDTO::new).toList(),
                cart.getCartItems().stream().mapToInt(item -> item.getQuantity()).sum(),
                cart.getCartItems().stream()
                        .map(cartItem -> Money.multiply(cartItem.getProduct().getPrice(), cartItem.getQuantity()))
                        .reduce(Money.zero(), BigDecimal::add)
        );
    }
}
