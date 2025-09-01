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
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody CreateProductDTO body) throws ResourceNotFoundException {
        ProductDTO savedProduct = productService.create(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getProducts(@ModelAttribute ProductQueryParams params) {
        List<ProductDTO> products = productService.getProducts(params);

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID productId) throws ResourceNotFoundException {
        ProductDTO product = productService.getById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/{productId}/details")
    public ResponseEntity<ProductDetailsDTO> getProductDetailsById(@PathVariable UUID productId) throws ResourceNotFoundException {
        ProductDetailsDTO product = productService.getDetailsById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID productId, @RequestBody @Valid UpdateProductDTO body) throws ResourceNotFoundException, IllegalArgumentException {
        ProductDTO updatedProduct = productService.update(productId, body);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productService.delete(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
