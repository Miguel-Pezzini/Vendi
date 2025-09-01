package com.vendi.user.controller;

import com.vendi.address.dto.AddressDTO;
import com.vendi.product.dto.ProductDTO;
import com.vendi.user.dto.UserDTO;
import com.vendi.user.service.MeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/me")
public class MeController {

    @Autowired
    MeService meService;

    @GetMapping
    public ResponseEntity<UserDTO> getUser() {
        return ResponseEntity.ok(meService.getMe());
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getUserProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(meService.getMyProducts());
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getUserAddresses() {
        return ResponseEntity.status(HttpStatus.OK).body(meService.getMyAddresses());
    }
}
