package com.vendi.checkout.controller;

import com.vendi.checkout.dto.CheckoutSessionRequestDTO;
import com.vendi.checkout.dto.CheckoutSessionResponseDTO;
import com.vendi.checkout.dto.CheckoutStatusResponseDTO;
import com.vendi.checkout.service.CheckoutService;
import com.vendi.checkout.stripe.StripeCheckoutGateway;
import com.vendi.shared.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final StripeCheckoutGateway stripeCheckoutGateway;

    @PostMapping("/session")
    public ResponseEntity<CheckoutSessionResponseDTO> createSession(@Valid @RequestBody CheckoutSessionRequestDTO body) {
        return ResponseEntity.ok(checkoutService.createCheckoutSession(body));
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<CheckoutStatusResponseDTO> getSessionStatus(@PathVariable String sessionId) throws ResourceNotFoundException {
        return ResponseEntity.ok(checkoutService.getCheckoutStatus(sessionId));
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signatureHeader
    ) {
        checkoutService.handleWebhook(stripeCheckoutGateway.parseWebhookEvent(payload, signatureHeader));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
