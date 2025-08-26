package com.vendi.dtoMocks;

import com.vendi.category.dto.CategoryRequestDTO;

import java.util.UUID;

public class CategoryMocker {

    private static CategoryRequestDTO createBaseCategory(String name, String description, UUID fatherCategoryId) {
        return new CategoryRequestDTO(name, description, fatherCategoryId);
    }

    static public CategoryRequestDTO createCategoryWithoutFather() {
        return createBaseCategory("Category Test", "Category description", null);
    }

    static public CategoryRequestDTO createCategoryWithFather(UUID fatherCategoryId) {
        return createBaseCategory("Category Test", "Category description", fatherCategoryId);
    }
}
