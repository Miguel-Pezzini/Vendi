package com.vendi.integration;

import com.vendi.category.model.Category;
import com.vendi.category.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CategoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testSaveProduct() {
        Category category = new Category("Test", "description");
        Category saved = categoryRepository.save(category);

        assertNotNull(saved.getId());
        assertEquals("Test", saved.getName());
    }
}