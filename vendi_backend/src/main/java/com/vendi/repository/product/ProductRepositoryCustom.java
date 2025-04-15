package com.vendi.repository.product;

import com.vendi.dto.product.ProductRequestDTO;
import com.vendi.model.product.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findAllByCustomFilter(ProductRequestDTO productRequestDTO);
}
