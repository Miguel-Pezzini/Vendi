package com.vendi.dtoMocks;

import com.vendi.photo.dto.CreatePhotoDTO;
import com.vendi.product.dto.CreateProductDTO;
import com.vendi.shared.money.Money;

import java.util.List;
import java.util.UUID;

public class ProductMocker {

    static public CreateProductDTO createProduct(List<CreatePhotoDTO> photosToCreate, List<UUID> categoriesIds) {
        return new CreateProductDTO("Product test", Money.of("50.50"), 5, 0, 0, photosToCreate, categoriesIds);
    }
}
