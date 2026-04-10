package com.vendi.user.controller;

import com.vendi.address.dto.AddressDTO;
import com.vendi.address.dto.UpsertAddressDTO;
import com.vendi.address.exception.AddressNotFoundException;
import com.vendi.address.service.AddressService;
import com.vendi.product.dto.ProductDTO;
import com.vendi.user.dto.UserDTO;
import com.vendi.user.service.MeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/me")
public class MeController {

    @Autowired
    MeService meService;

    @Autowired
    AddressService addressService;

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

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody UpsertAddressDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.create(body));
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(
            @PathVariable UUID addressId,
            @Valid @RequestBody UpsertAddressDTO body
    ) throws AddressNotFoundException {
        return ResponseEntity.ok(addressService.update(addressId, body));
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID addressId) throws AddressNotFoundException {
        addressService.delete(addressId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/addresses/{addressId}/active")
    public ResponseEntity<AddressDTO> setActiveAddress(@PathVariable UUID addressId) throws AddressNotFoundException {
        return ResponseEntity.ok(addressService.setActive(addressId));
    }
}
