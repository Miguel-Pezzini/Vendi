package com.vendi.order.dto;

import com.vendi.photo.dto.PhotoDTO;
import com.vendi.photo.model.Photo;
import com.vendi.product.model.Product;

import java.util.UUID;

public record OrderItemProductDTO(
        UUID id,
        String name,
        PhotoDTO mainPhoto
) {
    public OrderItemProductDTO(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getPhotos().stream()
                        .filter(Photo::getIsMain)
                        .findFirst()
                        .map(PhotoDTO::new)
                        .orElse(null)
        );
    }
}
