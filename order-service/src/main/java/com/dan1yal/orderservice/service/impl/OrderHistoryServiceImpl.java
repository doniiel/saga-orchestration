package com.dan1yal.orderservice.service.impl;

import com.dan1yal.orderservice.dto.OrderHistoryDto;
import com.dan1yal.orderservice.enums.OrderStatus;
import com.dan1yal.orderservice.exc.OrderHistoryNotFound;
import com.dan1yal.orderservice.mapper.OrderHistoryMapper;
import com.dan1yal.orderservice.model.OrderHistory;
import com.dan1yal.orderservice.repository.OrderHistoryRepository;
import com.dan1yal.orderservice.service.OrderHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderHistoryServiceImpl implements OrderHistoryService {
    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderHistoryMapper orderHistoryMapper;

    @Override
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
    public List<OrderHistoryDto> getOrderHistory(String orderId) {
        List<OrderHistory> orderHistories = orderHistoryRepository.findByOrderId(orderId);
        return orderHistoryMapper.toDtoList(orderHistories);
    }

    @Override
    public OrderHistoryDto updateOrderStatus(String id, String status) {
        OrderHistory orderHistory = orderHistoryRepository.findById(id).orElseThrow
                (() -> new OrderHistoryNotFound(String.format("Order history with id %s not found", id)));
        orderHistory.setStatus(OrderStatus.valueOf(status));
        OrderHistory savedOrderHistory = orderHistoryRepository.save(orderHistory);
        return orderHistoryMapper.toDto(savedOrderHistory);
    }

    @Override
    public void deleteOrderHistory(String orderId) {
        orderHistoryRepository.deleteByOrderId(orderId);
    }
}
