package com.vendi.product.mapper;

import com.vendi.product.dto.CreateProductRequestDTO;
import com.vendi.photo.model.Photo;
import com.vendi.category.model.Category;
import com.vendi.product.model.Product;
import com.vendi.user.model.User;

import java.util.List;
import java.util.Set;

public class ProductMapper {

    public static Product mapToProduct(CreateProductRequestDTO createProductRequestDTO) {
        Product product = new Product();
        product.setQuantity(createProductRequestDTO.quantity());
        product.setPrice(createProductRequestDTO.price());
        product.setInstallment(createProductRequestDTO.installment());
        product.setDiscount(createProductRequestDTO.discount());
        product.setName(createProductRequestDTO.name());
        return product;
    }
}
