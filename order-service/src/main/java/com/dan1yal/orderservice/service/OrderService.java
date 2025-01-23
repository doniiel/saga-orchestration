package com.dan1yal.orderservice.service;

import com.dan1yal.orderservice.dto.OrderDto;
import com.dan1yal.orderservice.request.OrderRequest;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderRequest request);

    OrderDto getOrderById(String id);

    List<OrderDto> getAllOrders();

    void deleteOrder(String id);

    void cancelOrder(String orderId);
    void completeOrder(String orderId);
}
