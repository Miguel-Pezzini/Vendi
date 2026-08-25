package com.vendi.order.controller;

import com.vendi.order.dto.OrderDetailsResponseDTO;
import com.vendi.order.dto.OrderSummaryResponseDTO;
import com.vendi.order.service.OrderService;
import com.vendi.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderSummaryResponseDTO>> getMyOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(orderService.getMyOrders(page, size));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailsResponseDTO> getMyOrderById(@PathVariable UUID orderId) throws ResourceNotFoundException {
        return ResponseEntity.ok(orderService.getMyOrderById(orderId));
    }
}
