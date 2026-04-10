package com.vendi.checkout.stripe;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StripeCheckoutGatewayImpl implements StripeCheckoutGateway {

    private final String secretKey;
    private final String webhookSecret;

    public StripeCheckoutGatewayImpl(
            @Value("${stripe.secret-key:}") String secretKey,
            @Value("${stripe.webhook-secret:}") String webhookSecret
    ) {
        this.secretKey = secretKey;
        this.webhookSecret = webhookSecret;
    }

    @Override
    public StripeCheckoutSession createCheckoutSession(StripeCheckoutSessionRequest request) {
        if (secretKey == null || secretKey.isBlank()) {
            throw new IllegalStateException("Stripe secret key is not configured.");
        }

        try {
            SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(request.successUrl())
                    .setCancelUrl(request.cancelUrl())
                    .setCustomerEmail(request.customerEmail())
                    .putMetadata("orderId", request.orderId().toString())
                    .putMetadata("customerName", request.customerName())
                    .putMetadata("phone", request.phone())
                    .putMetadata("addressLine1", request.addressLine1())
                    .putMetadata("addressLine2", safeMetadataValue(request.addressLine2()))
                    .putMetadata("city", request.city());

            for (StripeCheckoutSessionItem item : request.items()) {
                paramsBuilder.addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(item.quantity())
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("brl")
                                                .setUnitAmount(item.unitAmountInCents())
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(item.name())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                );
            }

            Session session = Session.create(
                    paramsBuilder.build(),
                    RequestOptions.builder().setApiKey(secretKey).build()
            );

            return new StripeCheckoutSession(session.getId(), session.getUrl());
        } catch (StripeException e) {
            throw new IllegalStateException("Could not create Stripe checkout session.", e);
        }
    }

    @Override
    public StripeWebhookEvent parseWebhookEvent(String payload, String signatureHeader) {
        if (webhookSecret == null || webhookSecret.isBlank()) {
            throw new IllegalStateException("Stripe webhook secret is not configured.");
        }

        try {
            Event event = Webhook.constructEvent(payload, signatureHeader, webhookSecret);
            StripeObject stripeObject = event.getDataObjectDeserializer().getObject().orElse(null);

            if (!(stripeObject instanceof Session session)) {
                return new StripeWebhookEvent(event.getType(), null, null);
            }

            return new StripeWebhookEvent(event.getType(), session.getId(), session.getPaymentIntent());
        } catch (SignatureVerificationException e) {
            throw new IllegalArgumentException("Invalid Stripe webhook signature.", e);
        }
    }

    private String safeMetadataValue(String value) {
        return value == null ? "" : value;
    }
}
