package com.vendi.order.dto;

import com.vendi.order.model.Order;
import com.vendi.order.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderSummaryResponseDTO(
        UUID id,
        OrderStatus status,
        Float totalAmount,
        String customerName,
        String email,
        String paymentProvider,
        LocalDateTime createdAt,
        int totalItems
) {
    public OrderSummaryResponseDTO(Order order) {
        this(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCustomerName(),
                order.getEmail(),
                order.getPaymentProvider(),
                order.getCreatedAt(),
                order.getOrderItems().stream()
                        .mapToInt(orderItem -> orderItem.getQuantity())
                        .sum()
        );
    }
}
