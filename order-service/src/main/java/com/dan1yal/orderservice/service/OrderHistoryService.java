package com.dan1yal.orderservice.service;

import com.dan1yal.orderservice.dto.OrderHistoryDto;

import java.util.List;

public interface OrderHistoryService {

    OrderHistoryDto createOrderHistory(String orderId);
    List<OrderHistoryDto> getOrderHistory(String orderId);

    OrderHistoryDto updateOrderStatus(String id, String status);
    void deleteOrderHistory(String orderId);

}
