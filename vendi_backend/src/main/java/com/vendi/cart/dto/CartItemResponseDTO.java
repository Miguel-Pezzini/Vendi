package com.vendi.cart.dto;

import com.vendi.cart.model.CartItem;
import com.vendi.product.dto.ProductDTO;
import com.vendi.shared.money.Money;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemResponseDTO(
        UUID id,
        int quantity,
        BigDecimal subtotal,
        ProductDTO product
) {
    public CartItemResponseDTO(CartItem cartItem) {
        this(
                cartItem.getId(),
                cartItem.getQuantity(),
                Money.multiply(cartItem.getProduct().getPrice(), cartItem.getQuantity()),
                new ProductDTO(cartItem.getProduct())
        );
    }
}
