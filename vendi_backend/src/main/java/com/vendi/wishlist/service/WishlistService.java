package com.vendi.wishlist.service;

import com.vendi.product.model.Product;
import com.vendi.product.repository.ProductRepository;
import com.vendi.shared.exception.ResourceNotFoundException;
import com.vendi.user.model.User;
import com.vendi.user.service.UserAuthenticatedService;
import com.vendi.wishlist.dto.AddWishlistItemRequestDTO;
import com.vendi.wishlist.dto.WishlistResponseDTO;
import com.vendi.wishlist.model.Wishlist;
import com.vendi.wishlist.model.WishlistItem;
import com.vendi.wishlist.repository.WishlistItemRepository;
import com.vendi.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final ProductRepository productRepository;
    private final UserAuthenticatedService userAuthenticatedService;

    @Transactional
    public WishlistResponseDTO getMyWishlist() {
        Wishlist wishlist = getOrCreateWishlist(userAuthenticatedService.getAuthenticatedUser());
        sortItems(wishlist);
        return new WishlistResponseDTO(wishlist);
    }

    @Transactional
    public WishlistResponseDTO addItem(AddWishlistItemRequestDTO body) throws ResourceNotFoundException {
        User user = userAuthenticatedService.getAuthenticatedUser();
        Wishlist wishlist = getOrCreateWishlist(user);
        Product product = productRepository.findById(body.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));

        boolean alreadyPresent = wishlistItemRepository.findByWishlistIdAndProductId(wishlist.getId(), product.getId())
                .isPresent();

        if (!alreadyPresent) {
            WishlistItem wishlistItem = new WishlistItem();
            wishlistItem.setWishlist(wishlist);
            wishlistItem.setProduct(product);
            wishlistItem.setQuantity(1);
            wishlist.getWishlistItems().add(wishlistItem);
        }

        Wishlist savedWishlist = wishlistRepository.save(wishlist);
        sortItems(savedWishlist);
        return new WishlistResponseDTO(savedWishlist);
    }

    @Transactional
    public WishlistResponseDTO removeItemByProductId(UUID productId) throws ResourceNotFoundException {
        Wishlist wishlist = getOrCreateWishlist(userAuthenticatedService.getAuthenticatedUser());
        WishlistItem wishlistItem = wishlistItemRepository.findByWishlistIdAndProductId(wishlist.getId(), productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in wishlist."));

        wishlist.getWishlistItems().remove(wishlistItem);
        wishlistItemRepository.delete(wishlistItem);

        Wishlist savedWishlist = wishlistRepository.save(wishlist);
        sortItems(savedWishlist);
        return new WishlistResponseDTO(savedWishlist);
    }

    private Wishlist getOrCreateWishlist(User user) {
        return wishlistRepository.findByUserId(user.getId()).orElseGet(() -> {
            Wishlist wishlist = new Wishlist();
            wishlist.setUser(user);
            user.setWishlist(wishlist);
            return wishlistRepository.save(wishlist);
        });
    }

    private void sortItems(Wishlist wishlist) {
        wishlist.getWishlistItems().sort(Comparator.comparing(WishlistItem::getCreatedAt).reversed());
    }
}
