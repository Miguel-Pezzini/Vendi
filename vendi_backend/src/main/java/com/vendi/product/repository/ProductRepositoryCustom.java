package com.vendi.product.repository;

import com.vendi.product.dto.ProductQueryParams;
import com.vendi.product.model.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findAllByCustomFilter(ProductQueryParams productQueryParams);
}
