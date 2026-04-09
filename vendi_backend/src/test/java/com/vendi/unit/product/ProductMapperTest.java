package com.vendi.unit.product;

import com.vendi.category.model.Category;
import com.vendi.photo.dto.CreatePhotoDTO;
import com.vendi.photo.model.Photo;
import com.vendi.product.dto.CreateProductDTO;
import com.vendi.product.dto.ProductDTO;
import com.vendi.product.dto.ProductDetailsDTO;
import com.vendi.product.dto.UpdateProductDTO;
import com.vendi.product.exception.InvalidMainPhotoException;
import com.vendi.product.mapper.ProductMapper;
import com.vendi.product.model.Product;
import com.vendi.shared.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductMapperTest {

    @Test
    void createAndUpdateDtoMappingCopyEditableFields() {
        CreateProductDTO createProductDTO = new CreateProductDTO(
                "Keyboard",
                199.99f,
                5,
                3,
                10,
                List.of(photo(true, "main.png")),
                List.of(UUID.randomUUID())
        );
        Product product = ProductMapper.createDTOToProduct(createProductDTO);

        UpdateProductDTO updateProductDTO = new UpdateProductDTO(
                "Mechanical Keyboard",
                249.99f,
                8,
                6,
                15,
                List.of(UUID.randomUUID())
        );
        ProductMapper.updateDTOToProduct(updateProductDTO, product);

        assertEquals("Mechanical Keyboard", product.getName());
        assertEquals(249.99f, product.getPrice());
        assertEquals(8, product.getQuantity());
        assertEquals(6, product.getInstallment());
        assertEquals(15, product.getDiscount());
    }

    @Test
    void dtoConversionsExposeMainPhotoAndAllPhotos() {
        Product product = new Product();
        product.setName("Console");
        product.setPrice(2500f);
        product.setQuantity(2);
        product.setInstallment(4);
        product.setDiscount(5);

        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("Electronics description");
        product.setCategories(Set.of(category));

        Photo mainPhoto = buildPhoto(true, "main.png");
        Photo galleryPhoto = buildPhoto(false, "gallery.png");
        product.addPhoto(mainPhoto);
        product.addPhoto(galleryPhoto);

        ProductDTO productDTO = ProductMapper.toDTO(product);
        ProductDetailsDTO productDetailsDTO = ProductMapper.toDetailsDTO(product);

        assertEquals("Console", productDTO.name());
        assertEquals("main.png", productDTO.mainPhoto().filename());
        assertEquals(1, productDTO.categories().size());
        assertEquals(2, productDetailsDTO.photos().size());
    }

    @Test
    void validateCategoriesRejectsMissingCategoryIds() {
        HashSet<Category> categories = new HashSet<>();
        categories.add(new Category());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> ProductMapper.validateCategories(categories, List.of(UUID.randomUUID(), UUID.randomUUID()))
        );

        assertTrue(exception.getMessage().contains("Categories not found"));
    }

    @Test
    void validateMainPhotoRequiresExactlyOneMainPhoto() {
        InvalidMainPhotoException noMainPhoto = assertThrows(
                InvalidMainPhotoException.class,
                () -> ProductMapper.validateMainPhoto(List.of(photo(false, "gallery.png"), photo(false, "gallery-2.png")))
        );
        InvalidMainPhotoException duplicatedMainPhoto = assertThrows(
                InvalidMainPhotoException.class,
                () -> ProductMapper.validateMainPhoto(List.of(photo(true, "main.png"), photo(true, "main-2.png")))
        );

        assertTrue(noMainPhoto.getMessage().contains("found 0"));
        assertTrue(duplicatedMainPhoto.getMessage().contains("found 2"));
        assertDoesNotThrow(() -> ProductMapper.validateMainPhoto(List.of(photo(true, "main.png"), photo(false, "gallery.png"))));
    }

    private CreatePhotoDTO photo(boolean isMainPhoto, String filename) {
        String base64 = Base64.getEncoder().encodeToString(filename.getBytes(StandardCharsets.UTF_8));
        return new CreatePhotoDTO(isMainPhoto, base64, "image/png", filename);
    }

    private Photo buildPhoto(boolean isMainPhoto, String filename) {
        Photo photo = new Photo();
        photo.setFilename(filename);
        photo.setContentType("image/png");
        photo.setData(filename.getBytes(StandardCharsets.UTF_8));
        photo.setIsMain(isMainPhoto);
        return photo;
    }
}
