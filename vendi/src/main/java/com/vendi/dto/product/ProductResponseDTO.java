package com.vendi.dto.product;

import com.vendi.model.product.Product;

import java.util.UUID;

public record ProductResponseDTO(UUID id, String name, Float price, int quantity, int installment, int discount) {
    public ProductResponseDTO(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getInstallment(),
                product.getDiscount()
        );
    }
}
