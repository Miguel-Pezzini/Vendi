package com.vendi.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.product.dto.ProductDTO;
import com.vendi.product.dto.ProductDetailsDTO;
import com.vendi.user.model.UserRole;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductReadIntegrationTest extends AbstractIntegrationTest {

    @Test
    void getProductsSupportsSearchCategoryAndLimitWithoutAuthentication() throws Exception {
        String adminToken = bearerTokenFor(UserRole.ADMIN);
        CategoryResponseDTO electronics = createCategory("Electronics");
        CategoryResponseDTO books = createCategory("Books");

        createProductThroughApi(adminToken, "Phone Pro", 1200f, List.of(electronics.id()));
        createProductThroughApi(adminToken, "Phone Case", 45f, List.of(electronics.id()));
        createProductThroughApi(adminToken, "Novel Book", 35f, List.of(books.id()));

        String searchBody = mockMvc.perform(get("/products").param("search", "phone"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<ProductDTO> searchResults = objectMapper.readValue(searchBody, new TypeReference<>() {
        });

        String categoryBody = mockMvc.perform(get("/products").param("categoryId", books.id().toString()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<ProductDTO> categoryResults = objectMapper.readValue(categoryBody, new TypeReference<>() {
        });

        String limitBody = mockMvc.perform(get("/products").param("limit", "2"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<ProductDTO> limitedResults = objectMapper.readValue(limitBody, new TypeReference<>() {
        });

        assertEquals(2, searchResults.size());
        assertTrue(searchResults.stream().allMatch(product -> product.name().toLowerCase().contains("phone")));
        assertEquals(1, categoryResults.size());
        assertEquals("Novel Book", categoryResults.get(0).name());
        assertEquals(2, limitedResults.size());
    }

    @Test
    void getProductDetailsReturnsAllPhotosAndCategoriesWithoutAuthentication() throws Exception {
        String adminToken = bearerTokenFor(UserRole.ADMIN);
        CategoryResponseDTO electronics = createCategory("Electronics");
        ProductDTO createdProduct = createProductThroughApi(adminToken, "Console", 2200f, List.of(electronics.id()));

        String responseBody = mockMvc.perform(get("/products/{productId}/details", createdProduct.id()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductDetailsDTO productDetails = objectMapper.readValue(responseBody, ProductDetailsDTO.class);

        assertEquals(createdProduct.id(), productDetails.id());
        assertEquals(2, productDetails.photos().size());
        assertTrue(productDetails.categories().stream().anyMatch(category -> category.id().equals(electronics.id())));
    }

    @Test
    void deleteProductAsAdminRemovesProductFromPublicReads() throws Exception {
        String adminToken = bearerTokenFor(UserRole.ADMIN);
        CategoryResponseDTO electronics = createCategory("Electronics");
        ProductDTO createdProduct = createProductThroughApi(adminToken, "Tablet", 1500f, List.of(electronics.id()));

        mockMvc.perform(delete("/products/{productId}", createdProduct.id()).header("Authorization", adminToken))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/products/{productId}", createdProduct.id()))
                .andExpect(status().isNotFound());
    }
}
