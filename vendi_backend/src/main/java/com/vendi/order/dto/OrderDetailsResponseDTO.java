package com.vendi.order.dto;

import com.vendi.order.model.Order;
import com.vendi.order.model.OrderStatus;
import com.vendi.order.model.OrderStatusHistory;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public record OrderDetailsResponseDTO(
        UUID id,
        OrderStatus status,
        Float totalAmount,
        String paymentProvider,
        String stripeCheckoutSessionId,
        String stripePaymentIntentId,
        String customerName,
        String companyName,
        String email,
        String phone,
        String addressLine1,
        String addressLine2,
        String city,
        LocalDateTime createdAt,
        List<OrderItemResponseDTO> items,
        List<OrderStatusHistoryResponseDTO> statusHistory
) {
    public OrderDetailsResponseDTO(Order order) {
        this(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getPaymentProvider(),
                order.getStripeCheckoutSessionId(),
                order.getStripePaymentIntentId(),
                order.getCustomerName(),
                order.getCompanyName(),
                order.getEmail(),
                order.getPhone(),
                order.getAddressLine1(),
                order.getAddressLine2(),
                order.getCity(),
                order.getCreatedAt(),
                order.getOrderItems().stream()
                        .map(OrderItemResponseDTO::new)
                        .toList(),
                order.getOrderStatusHistory().stream()
                        .sorted(Comparator.comparing(OrderStatusHistory::getChangedAt).reversed())
                        .map(OrderStatusHistoryResponseDTO::new)
                        .toList()
        );
    }
}
