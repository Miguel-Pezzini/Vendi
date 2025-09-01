package com.vendi.integration;

import com.vendi.auth.service.AuthService;
import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.category.service.CategoryService;
import com.vendi.dtoMocks.AuthMocker;
import com.vendi.dtoMocks.CategoryMocker;
import com.vendi.dtoMocks.PhotoMocker;
import com.vendi.dtoMocks.ProductMocker;
import com.vendi.photo.dto.CreatePhotoDTO;
import com.vendi.product.dto.CreateProductDTO;
import com.vendi.product.dto.ProductDTO;
import com.vendi.product.service.ProductService;
import com.vendi.user.model.User;
import com.vendi.user.repository.UserRepository;
import com.vendi.user.service.UserAuthenticatedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

public class ProductIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    protected UserAuthenticatedService userAuthenticatedService;


    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        assertDoesNotThrow(() -> authService.register(AuthMocker.createRegisterUserDTO()));
        UserDetails user = userRepository.findByEmail(AuthMocker.createRegisterUserDTO().email());
        when(userAuthenticatedService.getAuthenticatedUser()).thenReturn((User) user);
    }

    @Test
    void testProductCreation() {
        List<CreatePhotoDTO> photosToCreateDTO = PhotoMocker.getPhotosToCreateDTO();
        CategoryResponseDTO categoryResponseDTO = categoryService.create(CategoryMocker.createCategoryWithoutFather());
        List<UUID> categoriesIds = List.of(categoryResponseDTO.id());
        CreateProductDTO createProductDTO = ProductMocker.createProduct(photosToCreateDTO, categoriesIds);
        ProductDTO productDTO = assertDoesNotThrow(() -> productService.create(createProductDTO));

        assertNotNull(productDTO.id());
        assertEquals(createProductDTO.name(), productDTO.name());
    }

    @Test
    void testProductUpdate() {
        List<CreatePhotoDTO> photosToCreateDTO = PhotoMocker.getPhotosToCreateDTO();
        CategoryResponseDTO categoryResponseDTO = categoryService.create(CategoryMocker.createCategoryWithoutFather());
        List<UUID> categoriesIds = List.of(categoryResponseDTO.id());
        CreateProductDTO createProductDTO = ProductMocker.createProduct(photosToCreateDTO, categoriesIds);
        ProductDTO productDTO = assertDoesNotThrow(() -> productService.create(createProductDTO));

        assertNotNull(productDTO.id());
        assertEquals(createProductDTO.name(), productDTO.name());
    }
}
