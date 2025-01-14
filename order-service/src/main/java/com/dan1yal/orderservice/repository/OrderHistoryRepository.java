package com.dan1yal.orderservice.repository;

import com.dan1yal.orderservice.dto.OrderHistoryDto;
import com.dan1yal.orderservice.model.OrderHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryRepository extends MongoRepository<OrderHistory, String> {
    List<OrderHistory> findByOrderId(String orderId);

    void deleteByOrderId(String orderId);
}
