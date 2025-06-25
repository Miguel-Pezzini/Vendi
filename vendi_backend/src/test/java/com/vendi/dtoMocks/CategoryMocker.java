package com.vendi.dtoMocks;

import com.vendi.category.dto.CategoryRequestDTO;

import java.util.UUID;

public class CategoryMocker {

    static public CategoryRequestDTO getRequestCategoryDTOWithoutCategoryFatherId() {
        return new CategoryRequestDTO("Category Test", "Category description", null);
    }

    static public CategoryRequestDTO getRequestCategoryDTO(UUID father_category_id) {
        return new CategoryRequestDTO("Category Test", "Category description", father_category_id);
    }
}
