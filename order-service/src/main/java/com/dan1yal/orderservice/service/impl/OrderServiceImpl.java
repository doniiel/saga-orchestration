package com.dan1yal.orderservice.service.impl;

import com.dan1yal.orderservice.dto.OrderDto;
import com.dan1yal.orderservice.event.order.OrderCreatedEvent;
import com.dan1yal.orderservice.mapper.OrderMapper;
import com.dan1yal.orderservice.model.Order;
import com.dan1yal.orderservice.repository.OrderRepository;
import com.dan1yal.orderservice.request.OrderRequest;
import com.dan1yal.orderservice.service.OrderHistoryService;
import com.dan1yal.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderHistoryService orderHistoryService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${orders.event.topic-name}")
    private String orderEventTopicName;

    @Override
    @Transactional
    public OrderDto createOrder(OrderRequest request) {
        Order newOrder = orderMapper.toOrder(request);
        Order savedOrder = orderRepository.save(newOrder);
        orderHistoryService.createOrderHistory(savedOrder.getOrderId());

        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(savedOrder.getOrderId())
                .productId(savedOrder.getProductId())
                .userId(savedOrder.getUserId())
                .quantity(savedOrder.getQuantity())
                .price(savedOrder.getPrice())
                .build();
        kafkaTemplate.send(orderEventTopicName, event);

        return orderMapper.toOrderDto(savedOrder);
    }

    @Override
    public OrderDto getOrderById(String id) {
        if (orderRepository.existsById(id)) {
            return orderMapper.toOrderDto(orderRepository.findById(id).get());
        }
        return null;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderMapper.toOrderDtoList(orderRepository.findAll());
    }

    @Override
    public void deleteOrder(String id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        }
    }

    @Override
    public void cancelOrder(Long orderId) {
        orderHistoryService.updateOrderStatus(orderId.toString(), "CANCELLED");
    }
}
