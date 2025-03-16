package com.vendi.dto.product;

import com.vendi.validation.PositiveFloat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProductRequestDTO(
        @NotNull  @Size(min = 1, max = 100) String name,
        @NotNull @PositiveFloat float price,
        @NotNull @Min(1) int quantity,
        int installment,
        int discount
) {}
