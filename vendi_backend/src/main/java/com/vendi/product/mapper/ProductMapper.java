package com.vendi.product.mapper;

import com.vendi.category.model.Category;
import com.vendi.photo.dto.CreatePhotoDTO;
import com.vendi.product.dto.CreateProductDTO;
import com.vendi.product.dto.ProductDTO;
import com.vendi.product.dto.ProductDetailsDTO;
import com.vendi.product.dto.UpdateProductDTO;
import com.vendi.product.exception.InvalidMainPhotoException;
import com.vendi.product.model.Product;
import com.vendi.shared.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(product);
    }

    public static ProductDetailsDTO toDetailsDTO(Product product) {
        return new ProductDetailsDTO(product);
    }

    public static Product createDTOToProduct(CreateProductDTO createProductDTO) {
        Product product = new Product();
        product.setQuantity(createProductDTO.quantity());
        product.setPrice(createProductDTO.price());
        product.setInstallment(createProductDTO.installment());
        product.setDiscount(createProductDTO.discount());
        product.setName(createProductDTO.name());
        return product;
    }

    public static Product updateDTOToProduct(UpdateProductDTO updateProductDTO, Product product) {
        product.setQuantity(updateProductDTO.quantity());
        product.setPrice(updateProductDTO.price());
        product.setInstallment(updateProductDTO.installment());
        product.setDiscount(updateProductDTO.discount());
        product.setName(updateProductDTO.name());
        return product;
    }

    public static void validateCategories(Set<Category> categories, List<UUID> categoriesIds) throws ResourceNotFoundException {
        if (categories.size() != categoriesIds.size()) {
            throw new ResourceNotFoundException("Categories not found");
        }
    }

    public static void validateMainPhoto(List<CreatePhotoDTO> photosToCreateDTO) {
        long mainPhotoCount = photosToCreateDTO.stream()
                .filter(CreatePhotoDTO::isMainPhoto)
                .count();

        if (mainPhotoCount != 1) {
            throw new InvalidMainPhotoException(
                    "There must be exactly one main photo, but found " + mainPhotoCount + ".");
        }
    }
}
