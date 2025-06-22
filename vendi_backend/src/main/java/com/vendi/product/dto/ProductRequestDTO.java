package com.vendi.product.dto;

import com.vendi.photo.dto.PhotoToCreateDTO;
import com.vendi.photo.dto.PhotoToKeepDTO;
import com.vendi.product.model.Product;
import com.vendi.validation.PositiveFloat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record ProductRequestDTO(
        @NotNull  @Size(min = 1, max = 100) String name,
        @NotNull @PositiveFloat float price,
        @NotNull @Min(1) int quantity,
        @Min(0) int installment,
        @Min(0) int discount,
        @NotNull @Size(max = Product.MAX_PHOTO_LIMIT, message = "You can send up to 5 photos only.")
        List<PhotoToCreateDTO> photosToCreate,
        List <PhotoToKeepDTO> photosToKeep,
        @NotNull List<UUID> categoriesIds
) {}
