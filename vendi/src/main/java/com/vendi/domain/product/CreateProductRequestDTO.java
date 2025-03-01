package com.vendi.domain.product;

public record CreateProductRequestDTO(String name, Float price, int quantity, int installment, int discount) {
}
