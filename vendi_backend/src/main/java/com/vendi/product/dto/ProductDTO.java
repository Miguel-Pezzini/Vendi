package com.vendi.product.dto;

import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.photo.dto.PhotoDTO;
import com.vendi.photo.model.Photo;
import com.vendi.product.model.Product;
import com.vendi.shared.money.Money;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

public record ProductDTO(UUID id, String name, BigDecimal price, int quantity, int installment, int discount, PhotoDTO mainPhoto, HashSet<CategoryResponseDTO> categories) {
    public ProductDTO(Product product) {
        this(
                product.getId(),
                product.getName(),
                Money.of(product.getPrice()),
                product.getQuantity(),
                product.getInstallment(),
                product.getDiscount(),
                product.getPhotos() == null ? null :
                        product.getPhotos().stream()
                                .filter(Photo::getIsMain)
                                .findFirst()
                                .map(PhotoDTO::new)
                                .orElse(null),
                product.getCategories() == null ? new HashSet<>() :
                        product.getCategories().stream()
                                .map(CategoryResponseDTO::new)
                                .collect(Collectors.toCollection(HashSet::new))
        );
    }
}
