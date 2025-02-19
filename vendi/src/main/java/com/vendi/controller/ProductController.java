package com.vendi.controller;

import com.vendi.domain.product.Product;
import com.vendi.domain.product.ProductRequestDTO;
import com.vendi.domain.product.ProductResponseDTO;
import com.vendi.domain.user.LoginRequestDTO;
import com.vendi.domain.user.LoginResponseDTO;
import com.vendi.domain.user.User;
import com.vendi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping()
    public ResponseEntity createProduct(@RequestBody ProductRequestDTO body) {
        Product savedProduct = productService.save(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductResponseDTO(savedProduct));
    }

    @GetMapping()
    public ResponseEntity getUserProducts() {
        List<ProductResponseDTO> products = productService.getUserProducts().stream().map(ProductResponseDTO::new).toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(products);
    }
}
