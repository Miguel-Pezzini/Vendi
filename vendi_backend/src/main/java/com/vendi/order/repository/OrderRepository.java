package com.vendi.order.repository;

import com.vendi.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByUserIdOrderByCreatedAtDesc(UUID userId);

    Optional<Order> findByIdAndUserId(UUID id, UUID userId);

    Optional<Order> findByStripeCheckoutSessionId(String stripeCheckoutSessionId);
    Optional<Order> findByStripeCheckoutSessionIdAndUserId(String stripeCheckoutSessionId, UUID userId);
}
