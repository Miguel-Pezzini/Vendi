package com.vendi.order.dto;

import com.vendi.order.model.OrderItem;

import java.util.UUID;

public record OrderItemResponseDTO(
        UUID id,
        int quantity,
        Float unitPrice,
        Float totalAmount,
        OrderItemProductDTO product
) {
    public OrderItemResponseDTO(OrderItem orderItem) {
        this(
                orderItem.getId(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                orderItem.getUnitPrice() * orderItem.getQuantity(),
                new OrderItemProductDTO(orderItem.getProduct())
        );
    }
}
