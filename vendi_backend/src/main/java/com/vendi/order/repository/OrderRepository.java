package com.vendi.order.repository;

import com.vendi.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<Order> findByStripeCheckoutSessionId(String stripeCheckoutSessionId);
    Optional<Order> findByStripeCheckoutSessionIdAndUserId(String stripeCheckoutSessionId, UUID userId);
}
