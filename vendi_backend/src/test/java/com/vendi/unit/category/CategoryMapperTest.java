package com.vendi.unit.category;

import com.vendi.category.dto.CategoryRequestDTO;
import com.vendi.category.mapper.CategoryMapper;
import com.vendi.category.model.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CategoryMapperTest {

    @Test
    void dtoToCategoryCopiesNameAndDescription() {
        CategoryRequestDTO requestDTO = new CategoryRequestDTO("Games", "Games description", null);

        Category category = CategoryMapper.dtoToCategory(requestDTO);

        assertEquals("Games", category.getName());
        assertEquals("Games description", category.getDescription());
        assertNull(category.getCategoryFather());
    }
}
