package com.vendi.product.mapper;

import com.vendi.product.dto.ProductRequestDTO;
import com.vendi.product.model.Product;

public class ProductMapper {

    public static Product mapToProduct(ProductRequestDTO productRequestDTO) {
        Product product = new Product();
        product.setQuantity(productRequestDTO.quantity());
        product.setPrice(productRequestDTO.price());
        product.setInstallment(productRequestDTO.installment());
        product.setDiscount(productRequestDTO.discount());
        product.setName(productRequestDTO.name());
        return product;
    }
}
