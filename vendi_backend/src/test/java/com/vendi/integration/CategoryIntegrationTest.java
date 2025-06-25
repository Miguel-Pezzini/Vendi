package com.vendi.integration;

import com.vendi.category.dto.CategoryRequestDTO;
import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.category.service.CategoryService;
import com.vendi.dtoMocks.CategoryMocker;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void testSaveCategoryWithoutCategoryFatherId() {
        CategoryRequestDTO categoryRequestDTO = CategoryMocker.getRequestCategoryDTOWithoutCategoryFatherId();
        CategoryResponseDTO saved = categoryService.create(categoryRequestDTO);

        assertNotNull(saved.id());
        assertEquals("Category Test", saved.name());
    }

    @Test
    void testSaveCategoryWithInvalidCategoryFatherId() {
        CategoryRequestDTO categoryRequestDTO = CategoryMocker.getRequestCategoryDTO(new UUID(0, 10));

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            categoryService.create(categoryRequestDTO);
        });

        String expectedMessage = "Father category not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testSaveCategoryWithCategoryFatherId() {
        CategoryRequestDTO fatherCategoryRequestDTO = CategoryMocker.getRequestCategoryDTOWithoutCategoryFatherId();
        CategoryResponseDTO fatherCategoryResponseDTO = categoryService.create(fatherCategoryRequestDTO);

        CategoryRequestDTO childCategoryRequestDTO = CategoryMocker.getRequestCategoryDTO(fatherCategoryResponseDTO.id());
        CategoryResponseDTO childCategoryResponseDTO = categoryService.create(childCategoryRequestDTO);

        assertNotNull(childCategoryResponseDTO.id());
        assertEquals(childCategoryResponseDTO.fatherCategoryId(), fatherCategoryResponseDTO.id());
    }
}