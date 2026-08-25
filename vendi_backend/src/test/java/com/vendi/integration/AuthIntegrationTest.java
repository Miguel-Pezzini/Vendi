package com.vendi.integration;

import com.vendi.auth.service.AuthService;
import com.vendi.dtoMocks.AuthMocker;
import com.vendi.shared.exception.ResourceAlreadyExistsException;
import com.vendi.user.dto.LoginRequestDTO;
import com.vendi.user.dto.LoginResponseDTO;
import com.vendi.user.dto.RegisterResponseDTO;
import com.vendi.user.dto.RegisterUserDTO;
import com.vendi.user.dto.UserDTO;
import com.vendi.user.model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    AuthService authService;

    @Test
    void testRegisterUser() {
        RegisterUserDTO registerUserDTO = AuthMocker.createRegisterUserDTO();
        RegisterResponseDTO registerResponseDTO = assertDoesNotThrow(() -> authService.register(registerUserDTO));

        assertNotNull(registerResponseDTO.token());
        assertTrue(registerResponseDTO.roles().contains("ROLE_USER"));
        assertFalse(registerResponseDTO.roles().contains("ROLE_ADMIN"));
    }

    @Test
    void testLoginUser() {
        assertDoesNotThrow(() -> authService.register(AuthMocker.createRegisterUserDTO()));
        LoginRequestDTO loginRequestDTO = AuthMocker.createLoginRequestDTO();
        LoginResponseDTO loginResponseDTO  = assertDoesNotThrow(() -> authService.login(loginRequestDTO));

        assertNotNull(loginResponseDTO.token());
    }

    @Test
    void testRegisteringTwoUserWithSameEmail() {
        assertDoesNotThrow(() -> authService.register(AuthMocker.createRegisterUserDTO()));

        Exception exception = assertThrows(ResourceAlreadyExistsException.class, () -> {
            authService.register(AuthMocker.createRegisterUserDTO());
        });

        String expectedMessage = "This email is already registered";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void registerHttpEndpointAlwaysCreatesUserRoleEvenIfAdminIsRequested() throws Exception {
        String responseBody = mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(Map.of(
                                        "name", "Admin Wannabe",
                                        "email", "wannabe-admin@vendi.test",
                                        "password", "123456",
                                        "role", "ADMIN"
                                )))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        RegisterResponseDTO registerResponse = objectMapper.readValue(responseBody, RegisterResponseDTO.class);
        assertTrue(registerResponse.roles().contains("ROLE_USER"));
        assertFalse(registerResponse.roles().contains("ROLE_ADMIN"));

        UserDTO me = objectMapper.readValue(
                mockMvc.perform(get("/me").header("Authorization", "Bearer " + registerResponse.token()))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                UserDTO.class
        );

        assertEquals("USER", me.role());
        assertEquals(UserRole.USER, ((com.vendi.user.model.User) userRepository.findByEmail("wannabe-admin@vendi.test")).getRole());
    }
}
