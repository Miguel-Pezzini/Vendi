package com.vendi.controller;

import com.vendi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping()
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("sucesso!");
    }
}
