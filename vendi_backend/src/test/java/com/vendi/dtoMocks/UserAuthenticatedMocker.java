package com.vendi.dtoMocks;

import com.vendi.user.dto.RegisterUserDTO;
import com.vendi.user.model.User;
import com.vendi.user.model.UserRole;

public class UserAuthenticatedMocker {
    public static User getAuthenticatedUser() {
        return new User("user@gmail.com", "123456", "user", UserRole.USER);
    }

    public static User getAuthenticatedAdmin() {
        return new User("admin@gmail.com", "123456", "admin", UserRole.ADMIN);
    }

    public static RegisterUserDTO getRegisterUserDTO() {
        return new RegisterUserDTO("user@gmail.com", "123456", "user", UserRole.USER);
    }
}
