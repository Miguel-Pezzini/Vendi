package com.vendi.integration;

import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.product.dto.ProductDTO;
import com.vendi.product.dto.UpdateProductDTO;
import com.vendi.user.model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductManagementApiIntegrationTest extends AbstractIntegrationTest {

    @Test
    void adminCanCreateAndUpdateProductsThroughApi() throws Exception {
        String adminToken = bearerTokenFor(UserRole.ADMIN);
        CategoryResponseDTO electronics = createCategory("Electronics");

        ProductDTO createdProduct = createProductThroughApi(adminToken, "Notebook", 3100f, List.of(electronics.id()));

        UpdateProductDTO updateProductDTO = new UpdateProductDTO(
                "Notebook Pro",
                3500f,
                8,
                6,
                15,
                List.of(electronics.id())
        );

        String updatedBody = mockMvc.perform(
                        put("/products/{productId}", createdProduct.id())
                                .header("Authorization", adminToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(updateProductDTO))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductDTO updatedProduct = objectMapper.readValue(updatedBody, ProductDTO.class);

        assertEquals(createdProduct.id(), updatedProduct.id());
        assertEquals("Notebook Pro", updatedProduct.name());
        assertEquals(3500f, updatedProduct.price());
        assertEquals(8, updatedProduct.quantity());
    }

    @Test
    void productWriteEndpointsRequireAdminRole() throws Exception {
        String adminToken = bearerTokenFor(UserRole.ADMIN);
        String userToken = bearerTokenFor(UserRole.USER);
        CategoryResponseDTO electronics = createCategory("Electronics");
        UpdateProductDTO updateProductDTO = new UpdateProductDTO(
                "Updated",
                1999f,
                4,
                2,
                5,
                List.of(electronics.id())
        );

        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(createProductDTO("Unauthorized", 100f, List.of(electronics.id()))))
                )
                .andExpect(status().isUnauthorized());

        mockMvc.perform(
                        post("/products")
                                .header("Authorization", userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(createProductDTO("Forbidden", 100f, List.of(electronics.id()))))
                )
                .andExpect(status().isForbidden());

        ProductDTO createdProduct = createProductThroughApi(adminToken, "Playable Console", 2000f, List.of(electronics.id()));

        mockMvc.perform(
                        put("/products/{productId}", createdProduct.id())
                                .header("Authorization", userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(updateProductDTO))
                )
                .andExpect(status().isForbidden());

        mockMvc.perform(delete("/products/{productId}", createdProduct.id()).header("Authorization", userToken))
                .andExpect(status().isForbidden());
    }
}
