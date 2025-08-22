package com.vendi.product.controller;

import com.vendi.product.dto.*;
import com.vendi.shared.exception.ResourceNotFoundException;
import com.vendi.product.service.ProductService;
import com.vendi.shared.exception.ValidationExceptions.IllegalArgumentException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping()
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO body) throws ResourceNotFoundException {
        ProductResponseDTO savedProduct = productService.create(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("/user")
    public ResponseEntity<List<ProductResponseDTO>> getUserProducts() {
        List<ProductResponseDTO> products = productService.getUserProducts();

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> getProducts(@ModelAttribute ProductQueryParams params) {
        List<ProductResponseDTO> products = productService.getProducts(params);

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable UUID productId) throws ResourceNotFoundException {
        ProductResponseDTO product = productService.getById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/{productId}/details")
    public ResponseEntity<ProductDetailsResponseDTO> getProductDetailsById(@PathVariable UUID productId) throws ResourceNotFoundException {
        ProductDetailsResponseDTO product = productService.getDetailsById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable UUID productId, @RequestBody @Valid ProductRequestDTO body) throws ResourceNotFoundException, IllegalArgumentException {
        ProductResponseDTO updatedProduct = productService.update(productId, body);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productService.delete(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
