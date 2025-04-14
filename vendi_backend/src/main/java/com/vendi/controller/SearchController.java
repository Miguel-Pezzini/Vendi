package com.vendi.controller;

import com.vendi.dto.product.ProductResponseDTO;
import com.vendi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    ProductService productService;

    @GetMapping("/latest")
    public ResponseEntity<List<ProductResponseDTO>> getLatestProducts(@RequestParam(required = false) Integer limit) {
        List<ProductResponseDTO> products = productService.getLatestProducts(limit);

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

}
