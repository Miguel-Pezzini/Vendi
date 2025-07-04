package com.vendi.category.service;

import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.category.dto.CategoryRequestDTO;
import com.vendi.category.mapper.CategoryMapper;
import com.vendi.category.model.Category;
import com.vendi.category.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public CategoryResponseDTO create(CategoryRequestDTO createCategoryDTO) {
        Category category = CategoryMapper.dtoToCategory(createCategoryDTO);
        if(createCategoryDTO.father_category_id() != null) {
            Category categoryFather = categoryRepository.findById(createCategoryDTO.father_category_id())
                    .orElseThrow(() -> new EntityNotFoundException("Father category not found"));
            category.setCategoryFather(categoryFather);
        }

        Category savedCategory = categoryRepository.save(category);
        return new CategoryResponseDTO(savedCategory);
    }

    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponseDTO::new)
                .collect(Collectors.toList());
    }


    public HashSet<Category> findAllById(@NotNull List<UUID> uuids) {
        return new HashSet<>(categoryRepository.findAllById(uuids));
    }
}
