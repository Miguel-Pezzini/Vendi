package com.vendi.dtoMocks;

import com.vendi.photo.dto.CreatePhotoDTO;
import com.vendi.photo.dto.PhotoToKeepDTO;
import com.vendi.product.dto.CreateProductDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductMocker {

    private static CreateProductDTO createBaseProduct(String name, float price, int quantity, int installment, int discount, List<CreatePhotoDTO> photosToCreate, List<PhotoToKeepDTO> photosToKeep, List<UUID> categoriesIds) {
        return new CreateProductDTO(name, price, quantity, installment, discount, photosToCreate, categoriesIds);
    }

//    static public ProductRequestDTO createCompleteProduct(List<PhotoToCreateDTO> photosToCreate, List<PhotoToKeepDTO> photosToKeep, List<UUID> categoriesIds) {
//        return createBaseProduct("Product test", 50.5F, 5, 0, 0, photosToCreate, photosToKeep, categoriesIds);
//    }

    static public CreateProductDTO createProduct(List<CreatePhotoDTO> photosToCreate, List<UUID> categoriesIds) {
        return createBaseProduct("Product test", 50.5F, 5, 0, 0, photosToCreate,new ArrayList<>(), categoriesIds);
    }
}
