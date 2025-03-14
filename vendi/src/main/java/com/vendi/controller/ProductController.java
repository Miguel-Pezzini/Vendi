package com.vendi.controller;

import com.vendi.model.product.Product;
import com.vendi.dto.CreateProductRequestDTO;
import com.vendi.dto.ProductResponseDTO;
import com.vendi.dto.UpdateProductRequestDTO;
import com.vendi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping()
    public ResponseEntity createProduct(@RequestBody CreateProductRequestDTO body) {
        Product savedProduct = productService.create(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductResponseDTO(savedProduct));
    }

    @GetMapping()
    public ResponseEntity getUserProducts() {
        List<ProductResponseDTO> products = productService.getUserProducts().stream().map(ProductResponseDTO::new).toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(products);
    }

    @PutMapping()
    public ResponseEntity updateProduct(@RequestParam("productId") UUID productId, @RequestBody UpdateProductRequestDTO body) {
        Product updatedProduct = productService.update(productId, body);

        return ResponseEntity.status(HttpStatus.CREATED).body(updatedProduct);
    }
}
