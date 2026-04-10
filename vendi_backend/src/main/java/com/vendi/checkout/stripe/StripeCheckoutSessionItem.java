package com.vendi.checkout.stripe;

public record StripeCheckoutSessionItem(
        String name,
        long unitAmountInCents,
        long quantity
) {
}
