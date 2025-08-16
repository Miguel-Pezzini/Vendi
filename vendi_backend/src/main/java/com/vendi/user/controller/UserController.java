package com.vendi.user.controller;

import com.vendi.user.dto.UserDTO;
import com.vendi.user.model.User;
import com.vendi.user.service.UserAuthenticatedService;
import com.vendi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @Autowired
    UserAuthenticatedService authenticatedService;

    @GetMapping()
    public ResponseEntity<UserDTO> getUser() {
        User user = authenticatedService.getAuthenticatedUser();
        return ResponseEntity.ok(new UserDTO(user));
    }
}
