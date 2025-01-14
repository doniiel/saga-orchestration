package com.dan1yal.orderservice.dto;

import com.dan1yal.orderservice.enums.OrderStatus;
import lombok.Data;

import java.util.Date;

@Data
public class OrderHistoryDto {
    private String orderHistoryId;
    private String orderId;
    private Date createdDate;
    private OrderStatus status;
}
