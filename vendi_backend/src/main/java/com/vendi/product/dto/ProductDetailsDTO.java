package com.vendi.product.dto;

import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.photo.dto.PhotoDTO;
import com.vendi.product.model.Product;
import com.vendi.shared.money.Money;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record ProductDetailsDTO(UUID id, String name, BigDecimal price, int quantity, int installment, int discount, List<PhotoDTO> photos, HashSet<CategoryResponseDTO> categories) {
    public ProductDetailsDTO(Product product) {
        this(
                product.getId(),
                product.getName(),
                Money.of(product.getPrice()),
                product.getQuantity(),
                product.getInstallment(),
                product.getDiscount(),
                product.getPhotos() == null ? List.of() : product.getPhotos().stream().map(PhotoDTO::new).collect(Collectors.toList()),
                product.getCategories() == null ? new HashSet<>() :
                        product.getCategories().stream()
                                .map(CategoryResponseDTO::new)
                                .collect(Collectors.toCollection(HashSet::new))
        );
    }
}
