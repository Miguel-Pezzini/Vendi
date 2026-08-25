package com.vendi.checkout.service;

import com.vendi.cart.model.Cart;
import com.vendi.cart.model.CartItem;
import com.vendi.cart.repository.CartRepository;
import com.vendi.checkout.dto.CheckoutSessionRequestDTO;
import com.vendi.checkout.dto.CheckoutSessionResponseDTO;
import com.vendi.checkout.dto.CheckoutStatusResponseDTO;
import com.vendi.checkout.model.StripeProcessedEvent;
import com.vendi.checkout.repository.StripeProcessedEventRepository;
import com.vendi.checkout.stripe.StripeCheckoutGateway;
import com.vendi.checkout.stripe.StripeCheckoutSession;
import com.vendi.checkout.stripe.StripeCheckoutSessionItem;
import com.vendi.checkout.stripe.StripeCheckoutSessionRequest;
import com.vendi.checkout.stripe.StripeWebhookEvent;
import com.vendi.order.model.Order;
import com.vendi.order.model.OrderItem;
import com.vendi.order.model.OrderStatus;
import com.vendi.order.model.OrderStatusHistory;
import com.vendi.order.repository.OrderRepository;
import com.vendi.product.repository.ProductRepository;
import com.vendi.shared.exception.InsufficientStockException;
import com.vendi.shared.exception.ResourceNotFoundException;
import com.vendi.shared.money.Money;
import com.vendi.user.model.User;
import com.vendi.user.service.UserAuthenticatedService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private static final String PAYMENT_PROVIDER = "STRIPE";

    private final UserAuthenticatedService userAuthenticatedService;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StripeProcessedEventRepository stripeProcessedEventRepository;
    private final StripeCheckoutGateway stripeCheckoutGateway;

    @Value("${app.frontend-base-url:http://localhost:3333}")
    private String frontendBaseUrl;

    @Transactional
    public CheckoutSessionResponseDTO createCheckoutSession(CheckoutSessionRequestDTO request) {
        User user = userAuthenticatedService.getAuthenticatedUser();
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("Cart is empty."));

        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty.");
        }

        List<CartItem> cartItems = List.copyOf(cart.getCartItems());
        reserveStock(cartItems);

        Order order = buildPendingOrder(user, cartItems, request);
        Order savedOrder = orderRepository.save(order);

        StripeCheckoutSession stripeSession = stripeCheckoutGateway.createCheckoutSession(
                new StripeCheckoutSessionRequest(
                        savedOrder.getId(),
                        savedOrder.getEmail(),
                        savedOrder.getCustomerName(),
                        savedOrder.getPhone(),
                        savedOrder.getAddressLine1(),
                        savedOrder.getAddressLine2(),
                        savedOrder.getCity(),
                        buildSuccessUrl(),
                        buildCancelUrl(),
                        savedOrder.getOrderItems().stream()
                                .map(this::toStripeItem)
                                .toList()
                )
        );

        savedOrder.setStripeCheckoutSessionId(stripeSession.sessionId());
        Order updatedOrder = orderRepository.save(savedOrder);

        return new CheckoutSessionResponseDTO(updatedOrder.getId(), stripeSession.sessionId(), stripeSession.checkoutUrl());
    }

    @Transactional(readOnly = true)
    public CheckoutStatusResponseDTO getCheckoutStatus(String sessionId) throws ResourceNotFoundException {
        User user = userAuthenticatedService.getAuthenticatedUser();
        Order order = orderRepository.findByStripeCheckoutSessionIdAndUserId(sessionId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Checkout session not found."));

        return new CheckoutStatusResponseDTO(
                order.getId(),
                sessionId,
                order.getStatus(),
                order.getTotalAmount(),
                order.getCustomerName(),
                order.getEmail()
        );
    }

    @Transactional
    public void handleWebhook(StripeWebhookEvent event) {
        if (alreadyProcessed(event.eventId())) {
            return;
        }
        if (event.sessionId() == null || event.sessionId().isBlank()) {
            markProcessed(event);
            return;
        }

        orderRepository.findByStripeCheckoutSessionId(event.sessionId())
                .ifPresent(order -> {
                    switch (event.eventType()) {
                        case "checkout.session.completed" -> markAsPaid(order, event.paymentIntentId());
                        case "checkout.session.expired" -> cancelAndRestore(order, OrderStatus.CANCELED);
                        case "checkout.session.async_payment_failed" -> cancelAndRestore(order, OrderStatus.PAYMENT_FAILED);
                        default -> {
                        }
                    }
                });

        markProcessed(event);
    }

    private Order buildPendingOrder(User user, List<CartItem> cartItems, CheckoutSessionRequestDTO request) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        order.setPaymentProvider(PAYMENT_PROVIDER);
        order.setCustomerName(request.firstName());
        order.setCompanyName(request.companyName());
        order.setEmail(request.email());
        order.setPhone(request.phone());
        order.setAddressLine1(request.address());
        order.setAddressLine2(request.additionalAddress());
        order.setCity(request.city());
        order.setTotalAmount(calculateTotal(cartItems));

        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> toOrderItem(order, cartItem))
                .toList();
        order.getOrderItems().addAll(orderItems);
        order.getOrderStatusHistory().add(createStatusHistory(order, OrderStatus.PENDING_PAYMENT));
        return order;
    }

    private void reserveStock(List<CartItem> items) {
        for (CartItem item : items) {
            int updated = productRepository.decrementStock(item.getProduct().getId(), item.getQuantity());
            if (updated == 0) {
                throw new InsufficientStockException("Not enough stock for this product.");
            }
        }
    }

    private void restoreStock(Order order) {
        for (OrderItem item : order.getOrderItems()) {
            productRepository.incrementStock(item.getProduct().getId(), item.getQuantity());
        }
    }

    private OrderItem toOrderItem(Order order, CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setUnitPrice(Money.of(cartItem.getProduct().getPrice()));
        return orderItem;
    }

    private StripeCheckoutSessionItem toStripeItem(OrderItem item) {
        return new StripeCheckoutSessionItem(
                item.getProduct().getName(),
                Money.toCents(item.getUnitPrice()),
                item.getQuantity()
        );
    }

    private BigDecimal calculateTotal(List<CartItem> items) {
        return items.stream()
                .map(item -> Money.multiply(item.getProduct().getPrice(), item.getQuantity()))
                .reduce(Money.zero(), BigDecimal::add);
    }

    private void markAsPaid(Order order, String paymentIntentId) {
        order.setStripePaymentIntentId(paymentIntentId);
        if (!updateStatus(order, OrderStatus.PAID)) {
            return;
        }

        Cart cart = order.getUser().getCart();
        if (cart != null) {
            cart.getCartItems().clear();
            cartRepository.save(cart);
        }
    }

    private void cancelAndRestore(Order order, OrderStatus newStatus) {
        if (updateStatus(order, newStatus)) {
            restoreStock(order);
        }
    }

    private boolean alreadyProcessed(String eventId) {
        return eventId != null && !eventId.isBlank() && stripeProcessedEventRepository.existsById(eventId);
    }

    private void markProcessed(StripeWebhookEvent event) {
        if (event.eventId() == null || event.eventId().isBlank()) {
            return;
        }
        try {
            stripeProcessedEventRepository.save(new StripeProcessedEvent(event.eventId(), event.eventType()));
        } catch (DataIntegrityViolationException ignored) {
            // Duplicate webhook deliveries are ignored after the first successful persist.
        }
    }

    private boolean updateStatus(Order order, OrderStatus newStatus) {
        if (order.getStatus() == newStatus) {
            return false;
        }
        if (order.getStatus() == OrderStatus.PAID && newStatus != OrderStatus.PAID) {
            return false;
        }

        order.setStatus(newStatus);
        order.getOrderStatusHistory().add(createStatusHistory(order, newStatus));
        orderRepository.save(order);
        return true;
    }

    private OrderStatusHistory createStatusHistory(Order order, OrderStatus status) {
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(order);
        history.setStatus(status);
        history.setChangedAt(new Date());
        return history;
    }

    private String buildSuccessUrl() {
        return normalizedFrontendBaseUrl() + "/checkout?status=success&session_id={CHECKOUT_SESSION_ID}";
    }

    private String buildCancelUrl() {
        return normalizedFrontendBaseUrl() + "/checkout?status=canceled";
    }

    private String normalizedFrontendBaseUrl() {
        return frontendBaseUrl.endsWith("/") ? frontendBaseUrl.substring(0, frontendBaseUrl.length() - 1) : frontendBaseUrl;
    }
}
