package com.vendi.unit.checkout;

import com.vendi.cart.model.Cart;
import com.vendi.cart.model.CartItem;
import com.vendi.cart.repository.CartRepository;
import com.vendi.checkout.dto.CheckoutSessionRequestDTO;
import com.vendi.checkout.dto.CheckoutSessionResponseDTO;
import com.vendi.checkout.service.CheckoutService;
import com.vendi.checkout.stripe.StripeCheckoutGateway;
import com.vendi.checkout.stripe.StripeCheckoutSession;
import com.vendi.checkout.stripe.StripeWebhookEvent;
import com.vendi.order.model.Order;
import com.vendi.order.model.OrderStatus;
import com.vendi.order.repository.OrderRepository;
import com.vendi.product.model.Product;
import com.vendi.user.model.User;
import com.vendi.user.model.UserRole;
import com.vendi.user.service.UserAuthenticatedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {

    @Mock
    private UserAuthenticatedService userAuthenticatedService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private StripeCheckoutGateway stripeCheckoutGateway;

    @InjectMocks
    private CheckoutService checkoutService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(checkoutService, "frontendBaseUrl", "http://localhost:3333");
    }

    @Test
    void createCheckoutSessionRejectsEmptyCart() {
        User user = new User("user@vendi.test", "secret", "User", UserRole.USER);
        ReflectionTestUtils.setField(user, "id", UUID.randomUUID());

        when(userAuthenticatedService.getAuthenticatedUser()).thenReturn(user);
        when(cartRepository.findByUserId(user.getId())).thenReturn(Optional.of(new Cart()));

        assertThrows(IllegalStateException.class, () -> checkoutService.createCheckoutSession(validRequest()));
        verifyNoInteractions(orderRepository, stripeCheckoutGateway);
    }

    @Test
    void createCheckoutSessionBuildsPendingOrderAndStripeRequest() {
        User user = new User("user@vendi.test", "secret", "User", UserRole.USER);
        ReflectionTestUtils.setField(user, "id", UUID.randomUUID());

        Product product = new Product();
        ReflectionTestUtils.setField(product, "id", UUID.randomUUID());
        product.setName("Mouse");
        product.setPrice(79.9f);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(2);

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCartItems(new ArrayList<>(List.of(cartItem)));
        user.setCart(cart);

        when(userAuthenticatedService.getAuthenticatedUser()).thenReturn(user);
        when(cartRepository.findByUserId(user.getId())).thenReturn(Optional.of(cart));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            if (order.getId() == null) {
                ReflectionTestUtils.setField(order, "id", UUID.randomUUID());
            }
            return order;
        });
        when(stripeCheckoutGateway.createCheckoutSession(any()))
                .thenReturn(new StripeCheckoutSession("cs_unit", "https://checkout.stripe.com/pay/cs_unit"));

        CheckoutSessionResponseDTO response = checkoutService.createCheckoutSession(validRequest());

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository, atLeastOnce()).save(orderCaptor.capture());

        Order firstSavedOrder = orderCaptor.getAllValues().get(0);

        assertEquals("cs_unit", response.sessionId());
        assertEquals(OrderStatus.PENDING_PAYMENT, firstSavedOrder.getStatus());
        assertEquals(159.8f, firstSavedOrder.getTotalAmount());
        assertEquals(1, firstSavedOrder.getOrderItems().size());
        assertEquals(2, firstSavedOrder.getOrderItems().get(0).getQuantity());
        assertEquals("STRIPE", firstSavedOrder.getPaymentProvider());
    }

    @Test
    void completedWebhookMarksOrderAsPaidAndClearsCart() {
        User user = new User("user@vendi.test", "secret", "User", UserRole.USER);
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);

        Product product = new Product();
        product.setName("Monitor");
        product.setPrice(300f);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cart.setCartItems(new ArrayList<>(List.of(cartItem)));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        order.setStripeCheckoutSessionId("cs_paid");

        when(orderRepository.findByStripeCheckoutSessionId("cs_paid")).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        checkoutService.handleWebhook(new StripeWebhookEvent("checkout.session.completed", "cs_paid", "pi_123"));

        assertEquals(OrderStatus.PAID, order.getStatus());
        assertEquals("pi_123", order.getStripePaymentIntentId());
        assertEquals(0, cart.getCartItems().size());
    }

    private CheckoutSessionRequestDTO validRequest() {
        return new CheckoutSessionRequestDTO(
                "Miguel",
                "Vendi LTDA",
                "Rua A, 1",
                "Casa",
                "Sao Paulo",
                "+5511999999999",
                "miguel@vendi.test"
        );
    }
}
