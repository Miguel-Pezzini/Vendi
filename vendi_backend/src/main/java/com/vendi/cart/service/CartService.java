package com.vendi.cart.service;

import com.vendi.cart.dto.AddCartItemRequestDTO;
import com.vendi.cart.dto.CartResponseDTO;
import com.vendi.cart.model.Cart;
import com.vendi.cart.model.CartItem;
import com.vendi.cart.repository.CartItemRepository;
import com.vendi.cart.repository.CartRepository;
import com.vendi.product.model.Product;
import com.vendi.product.repository.ProductRepository;
import com.vendi.shared.exception.ResourceNotFoundException;
import com.vendi.user.model.User;
import com.vendi.user.service.UserAuthenticatedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserAuthenticatedService userAuthenticatedService;

    @Transactional
    public CartResponseDTO getMyCart() {
        Cart cart = getOrCreateCart(userAuthenticatedService.getAuthenticatedUser());
        return new CartResponseDTO(cart);
    }

    @Transactional
    public CartResponseDTO addItem(AddCartItemRequestDTO body) throws ResourceNotFoundException {
        User user = userAuthenticatedService.getAuthenticatedUser();
        Cart cart = getOrCreateCart(user);
        Product product = productRepository.findById(body.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    newItem.setQuantity(0);
                    cart.getCartItems().add(newItem);
                    return newItem;
                });

        cartItem.setQuantity(cartItem.getQuantity() + body.quantity());

        Cart savedCart = cartRepository.save(cart);
        return new CartResponseDTO(savedCart);
    }

    @Transactional
    public CartResponseDTO removeItemByProductId(java.util.UUID productId) throws ResourceNotFoundException {
        Cart cart = getOrCreateCart(userAuthenticatedService.getAuthenticatedUser());
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart."));

        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        return new CartResponseDTO(cartRepository.save(cart));
    }

    private Cart getOrCreateCart(User user) {
        return cartRepository.findByUserId(user.getId()).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            user.setCart(cart);
            return cartRepository.save(cart);
        });
    }
}
