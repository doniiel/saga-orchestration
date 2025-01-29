package com.dan1yal.orderservice.service.impl;

import com.dan1yal.orderservice.dto.OrderHistoryDto;
import com.dan1yal.orderservice.enums.OrderStatus;
import com.dan1yal.orderservice.exc.InvalidOrderStatusException;
import com.dan1yal.orderservice.exc.OrderHistoryNotFound;
import com.dan1yal.orderservice.mapper.OrderHistoryMapper;
import com.dan1yal.orderservice.model.OrderHistory;
import com.dan1yal.orderservice.repository.OrderHistoryRepository;
import com.dan1yal.orderservice.service.OrderHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "history")
public class OrderHistoryServiceImpl implements OrderHistoryService {
    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderHistoryMapper orderHistoryMapper;

    @Override
    @CachePut(key = "#result.orderId", unless = "#result == null")
    public OrderHistoryDto createOrderHistory(String orderId) {
        OrderHistory newOrderHistory = OrderHistory.builder()
                .orderId(orderId)
                .createdDate(new Date())
                .status(OrderStatus.CREATED)
                .build();
        OrderHistory savedOrderHistory = orderHistoryRepository.save(newOrderHistory);
        return orderHistoryMapper.toDto(savedOrderHistory);
    }

    @Override
    @Cacheable(key = "#orderId", unless = "#result == null")
    public List<OrderHistoryDto> getOrderHistory(String orderId) {
        List<OrderHistory> orderHistories = orderHistoryRepository.findByOrderId(orderId);
        return orderHistoryMapper.toDtoList(orderHistories);
    }

    @Override
    @CachePut(key = "#orderId")
    public OrderHistoryDto updateOrderStatus(String orderId, String status) {
        OrderHistory orderHistory = orderHistoryRepository.findById(orderId).orElseThrow
                (() -> new OrderHistoryNotFound(String.format("Order history with id %s not found", orderId)));
        try {
            orderHistory.setStatus(OrderStatus.valueOf(status));
        } catch (IllegalArgumentException e) {
            throw new InvalidOrderStatusException("Invalid order status: " + status);
        }
        OrderHistory savedOrderHistory = orderHistoryRepository.save(orderHistory);
        return orderHistoryMapper.toDto(savedOrderHistory);
    }

    @Override
    @CacheEvict(key = "#orderId")
    public void deleteOrderHistory(String orderId) {
        orderHistoryRepository.deleteByOrderId(orderId);
    }
}
