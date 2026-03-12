package com.vendi.cart.controller;

import com.vendi.cart.dto.AddCartItemRequestDTO;
import com.vendi.cart.dto.CartResponseDTO;
import com.vendi.cart.service.CartService;
import com.vendi.shared.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart() {
        return ResponseEntity.ok(cartService.getMyCart());
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponseDTO> addItem(@Valid @RequestBody AddCartItemRequestDTO body) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.addItem(body));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartResponseDTO> removeItem(@PathVariable UUID productId) throws ResourceNotFoundException {
        return ResponseEntity.ok(cartService.removeItemByProductId(productId));
    }
}
