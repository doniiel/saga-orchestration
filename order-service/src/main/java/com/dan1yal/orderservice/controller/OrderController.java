package com.dan1yal.orderservice.controller;

import com.dan1yal.orderservice.dto.OrderDto;
import com.dan1yal.orderservice.dto.OrderHistoryDto;
import com.dan1yal.orderservice.request.OrderRequest;
import com.dan1yal.orderservice.service.OrderHistoryService;
import com.dan1yal.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderHistoryService orderHistoryService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable String id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderHistoryDto> getOrderStatus(@PathVariable String id, @RequestBody String status) {
        return ResponseEntity.ok(orderHistoryService.updateOrderStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<?>> getOrderHistory(@PathVariable String id) {
        return ResponseEntity.ok(orderHistoryService.getOrderHistory(id));
    }

}
