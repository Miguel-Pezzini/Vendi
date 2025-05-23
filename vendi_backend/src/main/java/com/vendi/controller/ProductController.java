package com.vendi.controller;

import com.vendi.dto.product.ProductRequestDTO;
import com.vendi.exceptions.ResourceNotFoundException;
import com.vendi.model.product.Product;
import com.vendi.dto.product.CreateProductRequestDTO;
import com.vendi.dto.product.ProductResponseDTO;
import com.vendi.dto.product.UpdateProductRequestDTO;
import com.vendi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping()
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid CreateProductRequestDTO body) throws ResourceNotFoundException {
        ProductResponseDTO savedProduct = productService.create(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("/user")
    public ResponseEntity<List<ProductResponseDTO>> getUserProducts() {
        List<ProductResponseDTO> products = productService.getUserProducts();

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> getProducts(@ModelAttribute ProductRequestDTO dto) {
        List<ProductResponseDTO> products = productService.getProducts(dto);

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable UUID productId) throws ResourceNotFoundException {
        ProductResponseDTO product = productService.getById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable UUID productId, @RequestBody UpdateProductRequestDTO body) {
        ProductResponseDTO updatedProduct = productService.update(productId, body);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productService.delete(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
