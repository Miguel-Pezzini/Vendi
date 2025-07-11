package com.vendi.category.controller;
import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.category.dto.CategoryRequestDTO;
import com.vendi.category.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody @Valid CategoryRequestDTO body) {
        CategoryResponseDTO savedCategory = categoryService.create(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @GetMapping()
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> categories = categoryService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }
}
