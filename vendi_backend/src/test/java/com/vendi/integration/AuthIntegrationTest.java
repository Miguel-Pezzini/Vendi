package com.vendi.integration;

import com.vendi.auth.service.AuthService;
import com.vendi.dtoMocks.AuthMocker;
import com.vendi.shared.exception.ResourceAlreadyExistsException;
import com.vendi.user.dto.LoginRequestDTO;
import com.vendi.user.dto.LoginResponseDTO;
import com.vendi.user.dto.RegisterResponseDTO;
import com.vendi.user.dto.RegisterUserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class AuthIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    AuthService authService;

    @Test
    void testRegisterUser() {
        RegisterUserDTO registerUserDTO = AuthMocker.createRegisterUserDTO();
        RegisterResponseDTO registerResponseDTO = assertDoesNotThrow(() -> authService.register(registerUserDTO));

        assertNotNull(registerResponseDTO.token());
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
}
