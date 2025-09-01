package com.vendi.product.dto;

import com.vendi.photo.dto.CreatePhotoDTO;
import com.vendi.photo.dto.PhotoToKeepDTO;
import com.vendi.product.model.Product;
import com.vendi.validation.PositiveFloat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CreateProductDTO(
        @NotNull(message = "The name of the product cannot be empty.")  @Size(min = 1, max = 100) String name,
        @NotNull(message = "The price of the product cannot be empty.") @PositiveFloat float price,
        @NotNull(message = "The quantity of the product cannot be empty.")  @Min(1) int quantity,
        @Min(value = 0, message = "The installment can only be positive.") int installment,
        @Min(value = 0, message = "The discount can only be positive.") int discount,
        @NotNull @Size(max = Product.MAX_PHOTO_LIMIT, message = "You can send up to 5 photos only.") List<CreatePhotoDTO> photosToCreate,
        @Size(max = Product.MAX_PHOTO_LIMIT, message = "You can send up to 5 photos only.") List <PhotoToKeepDTO> photosToKeep,
        @NotNull(message = "You have to set at least one category for you product.") List<UUID> categoriesIds
) {}

