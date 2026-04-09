package com.vendi.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vendi.address.dto.AddressDTO;
import com.vendi.address.model.Address;
import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.product.dto.ProductDTO;
import com.vendi.user.dto.UserDTO;
import com.vendi.user.model.User;
import com.vendi.user.model.UserRole;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MeApiIntegrationTest extends AbstractIntegrationTest {

    @Test
    void meEndpointsRequireAuthentication() throws Exception {
        mockMvc.perform(get("/me")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/me/products")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/me/addresses")).andExpect(status().isUnauthorized());
    }

    @Test
    void getMeReturnsAuthenticatedUserProfile() throws Exception {
        User user = createUser(UserRole.USER);
        Address currentAddress = createAddress(user, "Main Street", "Curitiba");
        user.getAddresses().add(currentAddress);
        user.setCurrentAddress(currentAddress);
        userRepository.save(user);

        String responseBody = mockMvc.perform(get("/me").header("Authorization", bearerTokenFor(user)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDTO me = objectMapper.readValue(responseBody, UserDTO.class);

        assertEquals(user.getId(), me.id());
        assertEquals(user.getEmail(), me.email());
        assertEquals("USER", me.role());
        assertEquals("Main Street", me.currentAddress().street());
    }

    @Test
    void getMyProductsReturnsOnlyProductsOwnedByAuthenticatedUser() throws Exception {
        User admin = createUser(UserRole.ADMIN);
        String adminToken = bearerTokenFor(admin);
        CategoryResponseDTO electronics = createCategory("Electronics");

        createProductThroughApi(adminToken, "My Console", 2500f, List.of(electronics.id()));
        createProductThroughApi(bearerTokenFor(UserRole.ADMIN), "Other Console", 1800f, List.of(electronics.id()));

        String responseBody = mockMvc.perform(get("/me/products").header("Authorization", adminToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<ProductDTO> products = objectMapper.readValue(responseBody, new TypeReference<>() {
        });

        assertEquals(1, products.size());
        assertEquals("My Console", products.get(0).name());
    }

    @Test
    void getMyAddressesReturnsAuthenticatedUsersAddresses() throws Exception {
        User user = createUser(UserRole.USER);
        Address currentAddress = createAddress(user, "Main Street", "Curitiba");
        Address secondaryAddress = createAddress(user, "Second Street", "Sao Paulo");
        user.getAddresses().add(currentAddress);
        user.getAddresses().add(secondaryAddress);
        user.setCurrentAddress(currentAddress);
        userRepository.save(user);

        String responseBody = mockMvc.perform(get("/me/addresses").header("Authorization", bearerTokenFor(user)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<AddressDTO> addresses = objectMapper.readValue(responseBody, new TypeReference<>() {
        });

        assertEquals(2, addresses.size());
        assertTrue(addresses.stream().anyMatch(address -> address.street().equals("Main Street")));
        assertTrue(addresses.stream().anyMatch(address -> address.street().equals("Second Street")));
    }

    private Address createAddress(User user, String street, String city) {
        Address address = new Address();
        address.setUser(user);
        address.setStreet(street);
        address.setCity(city);
        address.setState("PR");
        address.setZipCode("80000-000");
        return address;
    }
}
