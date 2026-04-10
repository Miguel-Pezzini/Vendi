package com.vendi.order.dto;

import com.vendi.order.model.OrderStatus;
import com.vendi.order.model.OrderStatusHistory;

import java.util.Date;
import java.util.UUID;

public record OrderStatusHistoryResponseDTO(
        UUID id,
        OrderStatus status,
        Date changedAt
) {
    public OrderStatusHistoryResponseDTO(OrderStatusHistory history) {
        this(history.getId(), history.getStatus(), history.getChangedAt());
    }
}
