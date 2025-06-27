package com.vendi.integration;

import com.vendi.auth.service.AuthService;
import com.vendi.category.dto.CategoryRequestDTO;
import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.category.service.CategoryService;
import com.vendi.dtoMocks.CategoryMocker;
import com.vendi.dtoMocks.PhotoMocker;
import com.vendi.dtoMocks.ProductMocker;
import com.vendi.dtoMocks.UserAuthenticatedMocker;
import com.vendi.photo.dto.PhotoToCreateDTO;
import com.vendi.product.dto.ProductRequestDTO;
import com.vendi.product.dto.ProductResponseDTO;
import com.vendi.product.service.ProductService;
import com.vendi.shared.exception.ResourceAlreadyExistsException;
import com.vendi.shared.exception.ResourceNotFoundException;
import com.vendi.user.model.User;
import com.vendi.user.repository.UserRepository;
import com.vendi.user.service.UserAuthenticatedService;
import com.vendi.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
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
        assertDoesNotThrow(() -> authService.register(UserAuthenticatedMocker.getRegisterUserDTO()));
        UserDetails user = userRepository.findByEmail(UserAuthenticatedMocker.getRegisterUserDTO().email());
        when(userAuthenticatedService.getAuthenticatedUser()).thenReturn((User) user);
    }

    @Test
    void testProductServiceCreate() {
        List<PhotoToCreateDTO> photosToCreateDTO = PhotoMocker.getPhotosToCreateDTO();
        CategoryRequestDTO categoryRequestDTO = CategoryMocker.getCategoryRequestDTOWithoutCategoryFatherId();
        CategoryResponseDTO categoryResponseDTO = categoryService.create(categoryRequestDTO);
        List<UUID> categoriesIds = new ArrayList<>();
        categoriesIds.add(categoryResponseDTO.id());
        ProductRequestDTO productRequestDTO = ProductMocker.getProductRequestDTOWithPhotosToCreate(photosToCreateDTO, categoriesIds);
        ProductResponseDTO productResponseDTO = assertDoesNotThrow(() -> productService.create(productRequestDTO));

        assertNotNull(productResponseDTO.id());
        assertEquals(productRequestDTO.name(), productResponseDTO.name());
    }
}
