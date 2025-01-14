package com.dan1yal.orderservice.model;

import com.dan1yal.orderservice.enums.OrderStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("order_history")
public class OrderHistory {

    @Id
    private String orderHistoryId;
    private String  orderId;
    private Date createdDate;
    private OrderStatus status;
}
