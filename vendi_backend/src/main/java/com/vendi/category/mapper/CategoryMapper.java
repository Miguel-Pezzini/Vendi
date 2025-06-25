package com.vendi.category.mapper;

import com.vendi.category.dto.CategoryRequestDTO;
import com.vendi.category.model.Category;

public class CategoryMapper {

    public static Category dtoToCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category();
        category.setName(categoryRequestDTO.name());
        category.setDescription(categoryRequestDTO.description());
        return category;
    }
}
