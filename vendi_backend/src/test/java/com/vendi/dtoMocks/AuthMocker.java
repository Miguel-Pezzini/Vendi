package com.vendi.dtoMocks;

import com.vendi.user.dto.LoginRequestDTO;
import com.vendi.user.dto.RegisterUserDTO;
import com.vendi.user.model.User;
import com.vendi.user.model.UserRole;

public class AuthMocker {
    final static public String DEFAULT_PASSWORD = "123456";
    final static public String DEFAULT_USER_EMAIL = "user@gmail.com";
    final static public String DEFAULT_ADMIN_EMAIL = "admin@gmail.com";
    final static public String DEFAULT_USER_NAME = "user";
    final static public String DEFAULT_ADMIN_NAME = "admin";
    public static User createUser() {
        return new User(DEFAULT_USER_EMAIL, DEFAULT_PASSWORD, DEFAULT_USER_NAME, UserRole.USER);
    }

    public static User createAdmin() {
        return new User(DEFAULT_ADMIN_EMAIL, DEFAULT_PASSWORD, DEFAULT_ADMIN_NAME, UserRole.ADMIN);
    }

    public static RegisterUserDTO createRegisterUserDTO() {
        return new RegisterUserDTO(DEFAULT_USER_NAME, DEFAULT_USER_EMAIL, DEFAULT_PASSWORD, UserRole.USER);
    }

    public static LoginRequestDTO createLoginRequestDTO() {
        return new LoginRequestDTO(DEFAULT_USER_EMAIL, DEFAULT_PASSWORD);
    }
}
