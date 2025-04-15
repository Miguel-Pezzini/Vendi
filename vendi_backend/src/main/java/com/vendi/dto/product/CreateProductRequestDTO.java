package com.vendi.dto.product;

import com.vendi.dto.photo.CreatePhotoRequestDTO;
import com.vendi.validation.PositiveFloat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CreateProductRequestDTO(
        @NotNull  @Size(min = 1, max = 100) String name,
        @NotNull @PositiveFloat float price,
        @NotNull @Min(1) int quantity,
        @Min(0) int installment,
        @Min(0) int discount,
        @NotNull List<CreatePhotoRequestDTO> photos,
        @NotNull List<UUID> categoriesIds
) {}
