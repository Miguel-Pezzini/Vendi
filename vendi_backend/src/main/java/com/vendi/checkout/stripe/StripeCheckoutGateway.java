package com.vendi.checkout.stripe;

public interface StripeCheckoutGateway {
    StripeCheckoutSession createCheckoutSession(StripeCheckoutSessionRequest request);
    StripeWebhookEvent parseWebhookEvent(String payload, String signatureHeader);
}
