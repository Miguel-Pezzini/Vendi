package com.vendi.order.dto;

import com.vendi.order.model.OrderItem;
import com.vendi.shared.money.Money;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResponseDTO(
        UUID id,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal totalAmount,
        OrderItemProductDTO product
) {
    public OrderItemResponseDTO(OrderItem orderItem) {
        this(
                orderItem.getId(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                Money.multiply(orderItem.getUnitPrice(), orderItem.getQuantity()),
                new OrderItemProductDTO(orderItem.getProduct())
        );
    }
}
