package com.vendi.service;

import com.vendi.dto.category.CategoryResponseDTO;
import com.vendi.dto.category.CreateCategoryDTO;
import com.vendi.model.product.Category;
import com.vendi.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public CategoryResponseDTO create(CreateCategoryDTO createCategoryDTO) {
        Category category = new Category();
        category.setName(createCategoryDTO.name());
        category.setDescription(createCategoryDTO.description());
        if(createCategoryDTO.category_father_id() != null) {
            Category categoryFather = categoryRepository.findById(createCategoryDTO.category_father_id())
                    .orElseThrow(() -> new EntityNotFoundException("Father category not found"));
            category.setCategoryFather(categoryFather);
        }

        Category savedCategory = categoryRepository.save(category);
        return new CategoryResponseDTO(savedCategory.getId(), savedCategory.getName(), savedCategory.getDescription());
    }

    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryResponseDTO(
                        category.getId(),
                        category.getName(),
                        category.getDescription()))
                .collect(Collectors.toList());
    }


    public HashSet<Category> findAllById(@NotNull List<UUID> uuids) {
        return new HashSet<>(categoryRepository.findAllById(uuids));
    }
}
