package com.vendi.checkout.stripe;

import java.util.List;
import java.util.UUID;

public record StripeCheckoutSessionRequest(
        UUID orderId,
        String customerEmail,
        String customerName,
        String phone,
        String addressLine1,
        String addressLine2,
        String city,
        String successUrl,
        String cancelUrl,
        List<StripeCheckoutSessionItem> items
) {
}
