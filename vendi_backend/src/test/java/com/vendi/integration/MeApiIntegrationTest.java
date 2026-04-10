package com.vendi.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vendi.address.dto.AddressDTO;
import com.vendi.address.dto.UpsertAddressDTO;
import com.vendi.address.model.Address;
import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.product.dto.ProductDTO;
import com.vendi.user.dto.UserDTO;
import com.vendi.user.model.User;
import com.vendi.user.model.UserRole;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MeApiIntegrationTest extends AbstractIntegrationTest {

    @Test
    void meEndpointsRequireAuthentication() throws Exception {
        mockMvc.perform(get("/me")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/me/products")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/me/addresses")).andExpect(status().isUnauthorized());
        mockMvc.perform(post("/me/addresses").contentType(APPLICATION_JSON).content(asJson(addressRequest("Main Street"))))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(put("/me/addresses/" + UUID.randomUUID()).contentType(APPLICATION_JSON).content(asJson(addressRequest("Main Street"))))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(delete("/me/addresses/" + UUID.randomUUID())).andExpect(status().isUnauthorized());
        mockMvc.perform(put("/me/addresses/" + UUID.randomUUID() + "/active")).andExpect(status().isUnauthorized());
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

    @Test
    void createAddressCreatesAddressForAuthenticatedUserAndSetsFirstAsActive() throws Exception {
        User user = createUser(UserRole.USER);
        String bearerToken = bearerTokenFor(user);

        String responseBody = mockMvc.perform(post("/me/addresses")
                        .header("Authorization", bearerToken)
                        .contentType(APPLICATION_JSON)
                        .content(asJson(addressRequest("Rua Nova"))))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AddressDTO createdAddress = objectMapper.readValue(responseBody, AddressDTO.class);
        UserDTO me = getMe(bearerToken);
        List<AddressDTO> addresses = getMyAddresses(bearerToken);

        assertEquals("Rua Nova", createdAddress.street());
        assertEquals(1, addresses.size());
        assertNotNull(me.currentAddress());
        assertEquals(createdAddress.id(), me.currentAddress().id());
    }

    @Test
    void updateAddressUpdatesOnlyAuthenticatedUsersAddress() throws Exception {
        User owner = createUser(UserRole.USER);
        String bearerToken = bearerTokenFor(owner);
        Address address = createAddress(owner, "Main Street", "Curitiba");
        owner.getAddresses().add(address);
        owner.setCurrentAddress(address);
        userRepository.save(owner);
        AddressDTO persistedAddress = getMyAddresses(bearerToken).get(0);

        mockMvc.perform(put("/me/addresses/" + persistedAddress.id())
                        .header("Authorization", bearerToken)
                        .contentType(APPLICATION_JSON)
                        .content(asJson(new UpsertAddressDTO("Updated Street", "Londrina", "PR", "86000-000"))))
                .andExpect(status().isOk());

        AddressDTO updatedAddress = getMyAddresses(bearerToken).get(0);

        assertEquals("Updated Street", updatedAddress.street());
        assertEquals("Londrina", updatedAddress.city());
        assertEquals("86000-000", updatedAddress.zipCode());
    }

    @Test
    void updateAddressReturnsNotFoundWhenAddressBelongsToAnotherUser() throws Exception {
        User owner = createUser(UserRole.USER);
        User anotherUser = createUser(UserRole.USER);
        String anotherUserToken = bearerTokenFor(anotherUser);
        Address address = createAddress(owner, "Main Street", "Curitiba");
        owner.getAddresses().add(address);
        userRepository.save(owner);
        AddressDTO persistedAddress = getMyAddresses(bearerTokenFor(owner)).get(0);

        mockMvc.perform(put("/me/addresses/" + persistedAddress.id())
                        .header("Authorization", anotherUserToken)
                        .contentType(APPLICATION_JSON)
                        .content(asJson(addressRequest("Updated Street"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAddressRemovesAddressAndPromotesAnotherAddressWhenActiveOneIsDeleted() throws Exception {
        User user = createUser(UserRole.USER);
        String bearerToken = bearerTokenFor(user);
        Address currentAddress = createAddress(user, "Main Street", "Curitiba");
        Address secondaryAddress = createAddress(user, "Second Street", "Sao Paulo");
        user.getAddresses().add(currentAddress);
        user.getAddresses().add(secondaryAddress);
        user.setCurrentAddress(currentAddress);
        userRepository.save(user);
        List<AddressDTO> persistedAddresses = getMyAddresses(bearerToken);
        AddressDTO persistedCurrentAddress = persistedAddresses.stream()
                .filter(savedAddress -> savedAddress.street().equals("Main Street"))
                .findFirst()
                .orElseThrow();

        mockMvc.perform(delete("/me/addresses/" + persistedCurrentAddress.id())
                        .header("Authorization", bearerToken))
                .andExpect(status().isNoContent());

        List<AddressDTO> remainingAddresses = getMyAddresses(bearerToken);
        UserDTO me = getMe(bearerToken);

        assertEquals(1, remainingAddresses.size());
        assertEquals("Second Street", remainingAddresses.get(0).street());
        assertNotNull(me.currentAddress());
        assertEquals(remainingAddresses.get(0).id(), me.currentAddress().id());
    }

    @Test
    void deleteOnlyAddressClearsCurrentAddress() throws Exception {
        User user = createUser(UserRole.USER);
        String bearerToken = bearerTokenFor(user);
        Address currentAddress = createAddress(user, "Main Street", "Curitiba");
        user.getAddresses().add(currentAddress);
        user.setCurrentAddress(currentAddress);
        userRepository.save(user);
        AddressDTO persistedAddress = getMyAddresses(bearerToken).get(0);

        mockMvc.perform(delete("/me/addresses/" + persistedAddress.id())
                        .header("Authorization", bearerToken))
                .andExpect(status().isNoContent());

        List<AddressDTO> addresses = getMyAddresses(bearerToken);
        UserDTO me = getMe(bearerToken);

        assertTrue(addresses.isEmpty());
        assertNull(me.currentAddress());
    }

    @Test
    void setActiveAddressUpdatesCurrentAddress() throws Exception {
        User user = createUser(UserRole.USER);
        String bearerToken = bearerTokenFor(user);
        Address currentAddress = createAddress(user, "Main Street", "Curitiba");
        Address secondaryAddress = createAddress(user, "Second Street", "Sao Paulo");
        user.getAddresses().add(currentAddress);
        user.getAddresses().add(secondaryAddress);
        user.setCurrentAddress(currentAddress);
        userRepository.save(user);
        List<AddressDTO> persistedAddresses = getMyAddresses(bearerToken);
        AddressDTO persistedSecondaryAddress = persistedAddresses.stream()
                .filter(savedAddress -> savedAddress.street().equals("Second Street"))
                .findFirst()
                .orElseThrow();

        mockMvc.perform(put("/me/addresses/" + persistedSecondaryAddress.id() + "/active")
                        .header("Authorization", bearerToken))
                .andExpect(status().isOk());

        UserDTO me = getMe(bearerToken);
        List<AddressDTO> addresses = getMyAddresses(bearerToken);

        assertNotNull(me.currentAddress());
        assertEquals(persistedSecondaryAddress.id(), me.currentAddress().id());
        assertEquals(2, addresses.size());
    }

    @Test
    void createAddressValidatesRequestBody() throws Exception {
        User user = createUser(UserRole.USER);

        mockMvc.perform(post("/me/addresses")
                        .header("Authorization", bearerTokenFor(user))
                        .contentType(APPLICATION_JSON)
                        .content(asJson(new UpsertAddressDTO("", "Curitiba", "PR", "80000-000"))))
                .andExpect(status().isBadRequest());
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

    private UpsertAddressDTO addressRequest(String street) {
        return new UpsertAddressDTO(street, "Curitiba", "PR", "80000-000");
    }

    private UserDTO getMe(String bearerToken) throws Exception {
        String responseBody = mockMvc.perform(get("/me").header("Authorization", bearerToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(responseBody, UserDTO.class);
    }

    private List<AddressDTO> getMyAddresses(String bearerToken) throws Exception {
        String responseBody = mockMvc.perform(get("/me/addresses").header("Authorization", bearerToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(responseBody, new TypeReference<>() {
        });
    }
}
