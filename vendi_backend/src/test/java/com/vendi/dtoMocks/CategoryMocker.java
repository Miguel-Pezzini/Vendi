package com.vendi.dtoMocks;

import com.vendi.category.dto.CategoryRequestDTO;

import java.util.UUID;

public class CategoryMocker {

    private static CategoryRequestDTO getBaseCategoryRequestDTO(String name, String description, UUID fatherCategoryId) {
        return new CategoryRequestDTO(name, description, fatherCategoryId);
    }

    static public CategoryRequestDTO getCategoryRequestDTOWithoutCategoryFatherId() {
        return getBaseCategoryRequestDTO("Category Test", "Category description", null);
    }

    static public CategoryRequestDTO getCategoryRequestDTO(UUID fatherCategoryId) {
        return getBaseCategoryRequestDTO("Category Test", "Category description", fatherCategoryId);
    }
}
