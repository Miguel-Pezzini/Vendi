package com.vendi.domain.product;

public record UpdateProductRequestDTO(String name, Float price, Integer quantity, Integer installment, Integer discount) {
}
