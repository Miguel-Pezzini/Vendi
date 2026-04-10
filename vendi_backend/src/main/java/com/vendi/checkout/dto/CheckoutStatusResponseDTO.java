package com.vendi.checkout.dto;

import com.vendi.order.model.OrderStatus;

import java.util.UUID;

public record CheckoutStatusResponseDTO(
        UUID orderId,
        String sessionId,
        OrderStatus status,
        Float totalAmount,
        String customerName,
        String email
) {
}
