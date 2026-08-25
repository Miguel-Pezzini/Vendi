package com.vendi.checkout.stripe;

public record StripeWebhookEvent(
        String eventId,
        String eventType,
        String sessionId,
        String paymentIntentId
) {
}
