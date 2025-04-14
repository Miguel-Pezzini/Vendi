package com.vendi.controller;
import com.vendi.dto.category.CategoryResponseDTO;
import com.vendi.dto.category.CreateCategoryDTO;
import com.vendi.service.CategoryService;
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
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody @Valid CreateCategoryDTO body) {
        CategoryResponseDTO savedCategory = categoryService.create(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @GetMapping()
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> categories = categoryService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }
}
