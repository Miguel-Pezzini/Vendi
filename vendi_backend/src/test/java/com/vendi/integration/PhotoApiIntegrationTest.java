package com.vendi.integration;

import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.photo.dto.PhotoDataDTO;
import com.vendi.product.dto.ProductDTO;
import com.vendi.user.model.UserRole;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PhotoApiIntegrationTest extends AbstractIntegrationTest {

    @Test
    void getPhotoByIdReturnsDataUriForPublicRequests() throws Exception {
        String adminToken = bearerTokenFor(UserRole.ADMIN);
        CategoryResponseDTO electronics = createCategory("Electronics");
        ProductDTO createdProduct = createProductThroughApi(adminToken, "Camera", 950f, List.of(electronics.id()));

        String responseBody = mockMvc.perform(get("/photo/{photoId}", createdProduct.mainPhoto().id()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PhotoDataDTO photoData = objectMapper.readValue(responseBody, PhotoDataDTO.class);

        assertTrue(photoData.dataURI().startsWith("data:image/png;base64,"));
        assertEquals(createdProduct.mainPhoto().filename(), "Camera-main.png");
    }
}
