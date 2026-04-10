package com.vendi.checkout.dto;

import java.util.UUID;

public record CheckoutSessionResponseDTO(
        UUID orderId,
        String sessionId,
        String checkoutUrl
) {
}
