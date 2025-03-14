package com.vendi.dto;

public record UpdateProductRequestDTO(String name, Float price, Integer quantity, Integer installment, Integer discount) {
}
