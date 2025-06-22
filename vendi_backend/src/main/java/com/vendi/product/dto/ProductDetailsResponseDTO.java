package com.vendi.product.dto;

import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.photo.dto.PhotoResponseDTO;
import com.vendi.product.model.Product;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record ProductDetailsResponseDTO(UUID id, String name, Float price, int quantity, int installment, int discount, List<PhotoResponseDTO> photos, HashSet<CategoryResponseDTO> categories) {
    public ProductDetailsResponseDTO(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getInstallment(),
                product.getDiscount(),
                product.getPhotos().stream().map(PhotoResponseDTO::new).collect(Collectors.toList()),
                product.getCategories() == null ? new HashSet<>() :
                        product.getCategories().stream()
                                .map(CategoryResponseDTO::new)
                                .collect(Collectors.toCollection(HashSet::new))

        );
    }
}

