package com.vendi.order.service;

import com.vendi.order.dto.OrderDetailsResponseDTO;
import com.vendi.order.dto.OrderSummaryResponseDTO;
import com.vendi.order.repository.OrderRepository;
import com.vendi.shared.exception.ResourceNotFoundException;
import com.vendi.user.model.User;
import com.vendi.user.service.UserAuthenticatedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserAuthenticatedService userAuthenticatedService;

    @Transactional(readOnly = true)
    public List<OrderSummaryResponseDTO> getMyOrders() {
        User user = userAuthenticatedService.getAuthenticatedUser();
        return orderRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId()).stream()
                .map(OrderSummaryResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderDetailsResponseDTO getMyOrderById(UUID orderId) throws ResourceNotFoundException {
        User user = userAuthenticatedService.getAuthenticatedUser();
        return orderRepository.findByIdAndUserId(orderId, user.getId())
                .map(OrderDetailsResponseDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));
    }
}
