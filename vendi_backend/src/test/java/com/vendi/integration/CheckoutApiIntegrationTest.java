package com.vendi.integration;

import com.vendi.cart.dto.CartResponseDTO;
import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.checkout.dto.CheckoutSessionResponseDTO;
import com.vendi.checkout.dto.CheckoutStatusResponseDTO;
import com.vendi.checkout.stripe.StripeCheckoutGateway;
import com.vendi.checkout.stripe.StripeCheckoutSession;
import com.vendi.checkout.stripe.StripeWebhookEvent;
import com.vendi.order.model.OrderStatus;
import com.vendi.order.repository.OrderRepository;
import com.vendi.product.dto.ProductDTO;
import com.vendi.user.model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CheckoutApiIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @MockBean
    private StripeCheckoutGateway stripeCheckoutGateway;

    @Test
    void checkoutSessionEndpointsRequireAuthentication() throws Exception {
        mockMvc.perform(
                        post("/checkout/session")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(validCheckoutRequest()))
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createCheckoutSessionPersistsPendingOrderAndReturnsStripeUrl() throws Exception {
        String adminToken = bearerTokenFor(UserRole.ADMIN);
        String userToken = bearerTokenFor(UserRole.USER);
        CategoryResponseDTO books = createCategory("Books");
        ProductDTO product = createProductThroughApi(adminToken, "Clean Code", 149.9f, List.of(books.id()));

        mockMvc.perform(
                        post("/cart/items")
                                .header("Authorization", userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(Map.of("productId", product.id(), "quantity", 2)))
                )
                .andExpect(status().isOk());

        when(stripeCheckoutGateway.createCheckoutSession(any()))
                .thenReturn(new StripeCheckoutSession("cs_test_checkout", "https://checkout.stripe.com/pay/cs_test_checkout"));

        String responseBody = mockMvc.perform(
                        post("/checkout/session")
                                .header("Authorization", userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(validCheckoutRequest()))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CheckoutSessionResponseDTO response = objectMapper.readValue(responseBody, CheckoutSessionResponseDTO.class);
        CheckoutStatusResponseDTO persistedOrder = objectMapper.readValue(
                mockMvc.perform(
                                get("/checkout/session/{sessionId}", response.sessionId())
                                        .header("Authorization", userToken)
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                CheckoutStatusResponseDTO.class
        );

        assertEquals("cs_test_checkout", response.sessionId());
        assertEquals("https://checkout.stripe.com/pay/cs_test_checkout", response.checkoutUrl());
        assertEquals(OrderStatus.PENDING_PAYMENT, persistedOrder.status());
        assertEquals(299.8f, persistedOrder.totalAmount());
        assertEquals(1, orderRepository.findAll().size());
        assertEquals("cs_test_checkout", orderRepository.findAll().get(0).getStripeCheckoutSessionId());
    }

    @Test
    void webhookMarksOrderAsPaidAndClearsCart() throws Exception {
        String adminToken = bearerTokenFor(UserRole.ADMIN);
        String userToken = bearerTokenFor(UserRole.USER);
        CategoryResponseDTO electronics = createCategory("Electronics");
        ProductDTO product = createProductThroughApi(adminToken, "Keyboard", 100f, List.of(electronics.id()));

        mockMvc.perform(
                        post("/cart/items")
                                .header("Authorization", userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(Map.of("productId", product.id(), "quantity", 1)))
                )
                .andExpect(status().isOk());

        when(stripeCheckoutGateway.createCheckoutSession(any()))
                .thenReturn(new StripeCheckoutSession("cs_paid", "https://checkout.stripe.com/pay/cs_paid"));

        mockMvc.perform(
                        post("/checkout/session")
                                .header("Authorization", userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(validCheckoutRequest()))
                )
                .andExpect(status().isOk());

        when(stripeCheckoutGateway.parseWebhookEvent(eq("{\"id\":\"evt_123\"}"), eq("sig_test")))
                .thenReturn(new StripeWebhookEvent("checkout.session.completed", "cs_paid", "pi_123"));

        mockMvc.perform(
                        post("/checkout/webhook")
                                .header("Stripe-Signature", "sig_test")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":\"evt_123\"}")
                )
                .andExpect(status().isOk());

        CheckoutStatusResponseDTO statusResponse = objectMapper.readValue(
                mockMvc.perform(
                                get("/checkout/session/{sessionId}", "cs_paid")
                                        .header("Authorization", userToken)
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                CheckoutStatusResponseDTO.class
        );

        CartResponseDTO cart = objectMapper.readValue(
                mockMvc.perform(get("/cart").header("Authorization", userToken))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                CartResponseDTO.class
        );

        assertEquals(OrderStatus.PAID, statusResponse.status());
        assertEquals(0, cart.totalItems());
        assertTrue(cart.items().isEmpty());
    }

    private Map<String, Object> validCheckoutRequest() {
        return Map.of(
                "firstName", "Miguel",
                "companyName", "Vendi LTDA",
                "address", "Rua das Flores, 123",
                "additionalAddress", "Apto 42",
                "city", "Sao Paulo",
                "phone", "+5511999999999",
                "email", "miguel@vendi.test"
        );
    }
}
