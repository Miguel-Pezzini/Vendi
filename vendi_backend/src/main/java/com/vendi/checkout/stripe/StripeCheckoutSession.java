package com.vendi.checkout.stripe;

public record StripeCheckoutSession(
        String sessionId,
        String checkoutUrl
) {
}
