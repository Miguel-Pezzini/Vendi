package com.vendi.integration;

import com.vendi.cart.dto.CartResponseDTO;
import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.product.dto.ProductDTO;
import com.vendi.user.model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CartApiIntegrationTest extends AbstractIntegrationTest {

    @Test
    void cartEndpointsRequireAuthentication() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(
                        post("/cart/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(Map.of("productId", java.util.UUID.randomUUID(), "quantity", 1)))
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getCartCreatesAnEmptyCartForAuthenticatedUser() throws Exception {
        String userToken = bearerTokenFor(UserRole.USER);

        String responseBody = mockMvc.perform(get("/cart").header("Authorization", userToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CartResponseDTO cart = objectMapper.readValue(responseBody, CartResponseDTO.class);

        assertEquals(0, cart.totalItems());
        assertEquals(0f, cart.subtotal());
        assertEquals(0, cart.items().size());
    }

    @Test
    void addItemAggregatesQuantityAndSubtotalAndRemoveDeletesLine() throws Exception {
        String adminToken = bearerTokenFor(UserRole.ADMIN);
        String userToken = bearerTokenFor(UserRole.USER);
        CategoryResponseDTO electronics = createCategory("Electronics");
        ProductDTO product = createProductThroughApi(adminToken, "Headphones", 100f, List.of(electronics.id()));

        String firstAddBody = mockMvc.perform(
                        post("/cart/items")
                                .header("Authorization", userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(Map.of("productId", product.id(), "quantity", 2)))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        CartResponseDTO firstCart = objectMapper.readValue(firstAddBody, CartResponseDTO.class);

        String secondAddBody = mockMvc.perform(
                        post("/cart/items")
                                .header("Authorization", userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(Map.of("productId", product.id(), "quantity", 1)))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        CartResponseDTO secondCart = objectMapper.readValue(secondAddBody, CartResponseDTO.class);

        String removeBody = mockMvc.perform(
                        delete("/cart/items/{productId}", product.id())
                                .header("Authorization", userToken)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        CartResponseDTO emptiedCart = objectMapper.readValue(removeBody, CartResponseDTO.class);

        assertEquals(2, firstCart.totalItems());
        assertEquals(200f, firstCart.subtotal());
        assertEquals(1, firstCart.items().size());

        assertEquals(3, secondCart.totalItems());
        assertEquals(300f, secondCart.subtotal());
        assertEquals(3, secondCart.items().get(0).quantity());

        assertEquals(0, emptiedCart.totalItems());
        assertEquals(0f, emptiedCart.subtotal());
        assertEquals(0, emptiedCart.items().size());
    }
}
