package com.vendi.dtoMocks;

import com.vendi.category.dto.CategoryRequestDTO;
import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.photo.dto.PhotoToCreateDTO;
import com.vendi.photo.dto.PhotoToKeepDTO;
import com.vendi.product.dto.ProductRequestDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductMocker {

    private static ProductRequestDTO createBaseProduct(String name, float price, int quantity, int installment, int discount, List<PhotoToCreateDTO> photosToCreate, List<PhotoToKeepDTO> photosToKeep, List<UUID> categoriesIds) {
        return new ProductRequestDTO(name, price, quantity, installment, discount, photosToCreate, photosToKeep, categoriesIds);
    }

    static public ProductRequestDTO createCompleteProduct(List<PhotoToCreateDTO> photosToCreate, List<PhotoToKeepDTO> photosToKeep, List<UUID> categoriesIds) {
        return createBaseProduct("Product test", 50.5F, 5, 0, 0, photosToCreate, photosToKeep, categoriesIds);
    }

    static public ProductRequestDTO createProductWithoutPhotos(List<UUID> categoriesIds) {
        return createBaseProduct("Product test", 50.5F, 5, 0, 0, new ArrayList<>(),new ArrayList<>(), categoriesIds);
    }

    static public ProductRequestDTO createProductWithPhotosToCreate(List<PhotoToCreateDTO> photosToCreate, List<UUID> categoriesIds) {
        return createBaseProduct("Product test", 50.5F, 5, 0, 0, photosToCreate,new ArrayList<>(), categoriesIds);
    }
}
