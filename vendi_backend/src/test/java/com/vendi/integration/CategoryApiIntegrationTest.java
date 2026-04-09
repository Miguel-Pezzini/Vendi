package com.vendi.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vendi.category.dto.CategoryRequestDTO;
import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.user.model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryApiIntegrationTest extends AbstractIntegrationTest {

    @Test
    void getAllCategoriesReturnsPersistedCategoriesWithoutAuthentication() throws Exception {
        createCategory("Electronics");
        createCategory("Books");

        String responseBody = mockMvc.perform(get("/category"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<CategoryResponseDTO> categories = objectMapper.readValue(responseBody, new TypeReference<>() {
        });

        assertEquals(2, categories.size());
        assertTrue(categories.stream().anyMatch(category -> category.name().equals("Electronics")));
        assertTrue(categories.stream().anyMatch(category -> category.name().equals("Books")));
    }

    @Test
    void createCategoryRequiresAdminRole() throws Exception {
        String userToken = bearerTokenFor(UserRole.USER);
        String adminToken = bearerTokenFor(UserRole.ADMIN);
        CategoryRequestDTO requestDTO = new CategoryRequestDTO("Games", "Games description", null);

        mockMvc.perform(
                        post("/category")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(requestDTO))
                )
                .andExpect(status().isUnauthorized());

        mockMvc.perform(
                        post("/category")
                                .header("Authorization", userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(requestDTO))
                )
                .andExpect(status().isForbidden());

        String responseBody = mockMvc.perform(
                        post("/category")
                                .header("Authorization", adminToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(requestDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CategoryResponseDTO category = objectMapper.readValue(responseBody, CategoryResponseDTO.class);

        assertEquals("Games", category.name());
    }
}
