package com.vendi.wishlist.repository;

import com.vendi.wishlist.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, UUID> {
    Optional<WishlistItem> findByWishlistIdAndProductId(UUID wishlistId, UUID productId);
}
