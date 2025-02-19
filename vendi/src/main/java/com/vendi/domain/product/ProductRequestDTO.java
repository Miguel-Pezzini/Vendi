package com.vendi.domain.product;

public record ProductRequestDTO(String name, Float price, int quantity, int installment, int discount) {
}
