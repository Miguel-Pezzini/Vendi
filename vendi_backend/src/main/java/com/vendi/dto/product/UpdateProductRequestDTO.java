package com.vendi.dto.product;

import com.vendi.validation.PositiveFloat;
import jakarta.validation.constraints.Min;

public record UpdateProductRequestDTO(
        String name,
        @PositiveFloat Float price,
        @Min(1) Integer quantity,
        @Min(0) Integer installment,
        @Min(0) Integer discount) {
}
