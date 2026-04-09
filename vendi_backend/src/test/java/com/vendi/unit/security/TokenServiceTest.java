package com.vendi.unit.security;

import com.vendi.infra.security.TokenService;
import com.vendi.user.model.User;
import com.vendi.user.model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TokenServiceTest {

    @Test
    void generateTokenCanBeValidatedBackToTheUserEmail() {
        TokenService tokenService = new TokenService();
        ReflectionTestUtils.setField(tokenService, "secret", "unit-test-secret");
        User user = new User("admin@vendi.test", "123456", "admin", UserRole.ADMIN);

        String token = tokenService.generateToken(user);

        assertEquals("admin@vendi.test", tokenService.validateToken(token));
    }

    @Test
    void validateTokenReturnsNullWhenTokenIsInvalid() {
        TokenService tokenService = new TokenService();
        ReflectionTestUtils.setField(tokenService, "secret", "unit-test-secret");

        assertNull(tokenService.validateToken("invalid-token"));
    }
}
