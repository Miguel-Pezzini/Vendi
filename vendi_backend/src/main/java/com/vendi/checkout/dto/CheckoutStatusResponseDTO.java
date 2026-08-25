package com.vendi.checkout.dto;

import com.vendi.order.model.OrderStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record CheckoutStatusResponseDTO(
        UUID orderId,
        String sessionId,
        OrderStatus status,
        BigDecimal totalAmount,
        String customerName,
        String email
) {
}
