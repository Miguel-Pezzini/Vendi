package com.vendi.dto;

public record CreateProductRequestDTO(String name, Float price, int quantity, int installment, int discount) {
}
