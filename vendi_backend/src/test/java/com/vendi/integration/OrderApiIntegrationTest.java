package com.vendi.integration;

import com.vendi.category.dto.CategoryResponseDTO;
import com.vendi.checkout.dto.CheckoutSessionResponseDTO;
import com.vendi.checkout.stripe.StripeCheckoutGateway;
import com.vendi.checkout.stripe.StripeCheckoutSession;
import com.vendi.order.dto.OrderDetailsResponseDTO;
import com.vendi.order.dto.OrderSummaryResponseDTO;
import com.vendi.product.dto.ProductDTO;
import com.vendi.user.model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderApiIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @MockBean
    private StripeCheckoutGateway stripeCheckoutGateway;

    @Test
    void orderEndpointsRequireAuthentication() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authenticatedUserCanListAndInspectOwnOrders() throws Exception {
        String adminToken = bearerTokenFor(UserRole.ADMIN);
        String userToken = bearerTokenFor(UserRole.USER);
        CategoryResponseDTO books = createCategory("Books");
        ProductDTO product = createProductThroughApi(adminToken, "Domain-Driven Design", 200f, List.of(books.id()));

        mockMvc.perform(
                        post("/cart/items")
                                .header("Authorization", userToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(Map.of("productId", product.id(), "quantity", 2)))
                )
                .andExpect(status().isOk());

        when(stripeCheckoutGateway.createCheckoutSession(any()))
                .thenReturn(new StripeCheckoutSession("cs_order_list", "https://checkout.stripe.com/pay/cs_order_list"));

        CheckoutSessionResponseDTO createdOrder = objectMapper.readValue(
                mockMvc.perform(
                                post("/checkout/session")
                                        .header("Authorization", userToken)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(asJson(validCheckoutRequest()))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                CheckoutSessionResponseDTO.class
        );

        OrderSummaryResponseDTO[] orders = objectMapper.readValue(
                mockMvc.perform(get("/orders").header("Authorization", userToken))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                OrderSummaryResponseDTO[].class
        );

        OrderDetailsResponseDTO orderDetails = objectMapper.readValue(
                mockMvc.perform(get("/orders/{orderId}", createdOrder.orderId()).header("Authorization", userToken))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                OrderDetailsResponseDTO.class
        );

        assertEquals(1, orders.length);
        assertEquals(createdOrder.orderId(), orders[0].id());
        assertEquals(2, orders[0].totalItems());
        assertEquals("Miguel", orders[0].customerName());

        assertEquals(createdOrder.orderId(), orderDetails.id());
        assertEquals(1, orderDetails.items().size());
        assertEquals(2, orderDetails.items().get(0).quantity());
        assertEquals("Domain-Driven Design", orderDetails.items().get(0).product().name());
        assertFalse(orderDetails.statusHistory().isEmpty());
        assertEquals("Rua das Flores, 123", orderDetails.addressLine1());
    }

    @Test
    void authenticatedUserCannotInspectAnotherUsersOrder() throws Exception {
        String adminToken = bearerTokenFor(UserRole.ADMIN);
        String firstUserToken = bearerTokenFor(UserRole.USER);
        String secondUserToken = bearerTokenFor(UserRole.USER);
        CategoryResponseDTO books = createCategory("Books");
        ProductDTO product = createProductThroughApi(adminToken, "Refactoring", 180f, List.of(books.id()));

        mockMvc.perform(
                        post("/cart/items")
                                .header("Authorization", firstUserToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJson(Map.of("productId", product.id(), "quantity", 1)))
                )
                .andExpect(status().isOk());

        when(stripeCheckoutGateway.createCheckoutSession(any()))
                .thenReturn(new StripeCheckoutSession("cs_private_order", "https://checkout.stripe.com/pay/cs_private_order"));

        CheckoutSessionResponseDTO createdOrder = objectMapper.readValue(
                mockMvc.perform(
                                post("/checkout/session")
                                        .header("Authorization", firstUserToken)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(asJson(validCheckoutRequest()))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                CheckoutSessionResponseDTO.class
        );

        mockMvc.perform(get("/orders/{orderId}", createdOrder.orderId()).header("Authorization", secondUserToken))
                .andExpect(status().isNotFound());
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
