package com.vendi.integration;

import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.product.dto.ProductDTO;
import com.vendi.user.model.User;
import com.vendi.user.model.UserRole;
import com.vendi.wishlist.dto.WishlistResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WishlistApiIntegrationTest extends AbstractIntegrationTest {

    @Test
    void authenticatedUserCanPersistAndRemoveWishlistItems() throws Exception {
        User user = createUser(UserRole.USER);
        String bearerToken = bearerTokenFor(user);
        CategoryResponseDTO electronics = createCategory("Electronics");
        ProductDTO notebook = createProductThroughApi(
                bearerTokenFor(UserRole.ADMIN),
                "Notebook",
                3100f,
                List.of(electronics.id())
        );
        ProductDTO mouse = createProductThroughApi(
                bearerTokenFor(UserRole.ADMIN),
                "Mouse",
                150f,
                List.of(electronics.id())
        );

        WishlistResponseDTO wishlistAfterAdds = addWishlistItem(bearerToken, notebook.id());
        wishlistAfterAdds = addWishlistItem(bearerToken, mouse.id());
        wishlistAfterAdds = addWishlistItem(bearerToken, notebook.id());

        assertEquals(2, wishlistAfterAdds.totalItems());
        assertIterableEquals(
                List.of(mouse.id(), notebook.id()),
                wishlistAfterAdds.items().stream().map(item -> item.product().id()).toList()
        );

        String updatedBody = mockMvc.perform(
                        delete("/wishlist/items/{productId}", notebook.id())
                                .header("Authorization", bearerToken)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        WishlistResponseDTO wishlistAfterRemoval = objectMapper.readValue(updatedBody, WishlistResponseDTO.class);

        assertEquals(1, wishlistAfterRemoval.totalItems());
        assertEquals(mouse.id(), wishlistAfterRemoval.items().get(0).product().id());
    }

    @Test
    void wishlistIsScopedPerAuthenticatedUser() throws Exception {
        User firstUser = createUser(UserRole.USER);
        User secondUser = createUser(UserRole.USER);
        String firstToken = bearerTokenFor(firstUser);
        String secondToken = bearerTokenFor(secondUser);
        CategoryResponseDTO electronics = createCategory("Electronics");
        ProductDTO notebook = createProductThroughApi(
                bearerTokenFor(UserRole.ADMIN),
                "Notebook",
                3100f,
                List.of(electronics.id())
        );

        addWishlistItem(firstToken, notebook.id());

        String firstWishlistBody = mockMvc.perform(get("/wishlist").header("Authorization", firstToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String secondWishlistBody = mockMvc.perform(get("/wishlist").header("Authorization", secondToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        WishlistResponseDTO firstWishlist = objectMapper.readValue(firstWishlistBody, WishlistResponseDTO.class);
        WishlistResponseDTO secondWishlist = objectMapper.readValue(secondWishlistBody, WishlistResponseDTO.class);

        assertEquals(1, firstWishlist.totalItems());
        assertEquals(notebook.id(), firstWishlist.items().get(0).product().id());
        assertEquals(0, secondWishlist.totalItems());
    }

    @Test
    void wishlistEndpointsRequireAuthentication() throws Exception {
        mockMvc.perform(get("/wishlist"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(
                        post("/wishlist/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(new WishlistItemPayload(UUID.randomUUID())))
                )
                .andExpect(status().isUnauthorized());

        mockMvc.perform(delete("/wishlist/items/{productId}", UUID.randomUUID()))
                .andExpect(status().isUnauthorized());
    }

    private WishlistResponseDTO addWishlistItem(String bearerToken, UUID productId) throws Exception {
        String responseBody = mockMvc.perform(
                        post("/wishlist/items")
                                .header("Authorization", bearerToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(new WishlistItemPayload(productId)))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(responseBody, WishlistResponseDTO.class);
    }

    private record WishlistItemPayload(UUID productId) {
    }
}
