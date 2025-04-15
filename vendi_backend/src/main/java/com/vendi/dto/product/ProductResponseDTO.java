package com.vendi.dto.product;

import com.vendi.dto.category.CategoryResponseDTO;
import com.vendi.dto.photo.PhotoResponseDTO;
import com.vendi.model.product.Category;
import com.vendi.model.product.Product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record ProductResponseDTO(UUID id, String name, Float price, int quantity, int installment, int discount, PhotoResponseDTO mainPhoto, HashSet<CategoryResponseDTO> categories) {
    public ProductResponseDTO(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getInstallment(),
                product.getDiscount(),
                new PhotoResponseDTO(product.getMainPhoto()),
                product.getCategories() == null ? new HashSet<>() :
                        product.getCategories().stream()
                                .map(CategoryResponseDTO::new)
                                .collect(Collectors.toCollection(HashSet::new))

        );
    }
}
