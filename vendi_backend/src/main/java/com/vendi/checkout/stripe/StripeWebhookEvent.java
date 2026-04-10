package com.vendi.checkout.stripe;

public record StripeWebhookEvent(
        String eventType,
        String sessionId,
        String paymentIntentId
) {
}
