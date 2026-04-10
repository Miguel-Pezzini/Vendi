package com.vendi.wishlist.controller;

import com.vendi.shared.exception.ResourceNotFoundException;
import com.vendi.wishlist.dto.AddWishlistItemRequestDTO;
import com.vendi.wishlist.dto.WishlistResponseDTO;
import com.vendi.wishlist.service.WishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<WishlistResponseDTO> getWishlist() {
        return ResponseEntity.ok(wishlistService.getMyWishlist());
    }

    @PostMapping("/items")
    public ResponseEntity<WishlistResponseDTO> addItem(@Valid @RequestBody AddWishlistItemRequestDTO body) throws ResourceNotFoundException {
        return ResponseEntity.ok(wishlistService.addItem(body));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<WishlistResponseDTO> removeItem(@PathVariable UUID productId) throws ResourceNotFoundException {
        return ResponseEntity.ok(wishlistService.removeItemByProductId(productId));
    }
}
