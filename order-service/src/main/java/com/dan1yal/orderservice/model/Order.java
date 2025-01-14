package com.dan1yal.orderservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("order")
public class Order {

    @Id
    private String orderId;
    private String productId;
    private String userId;
    private Integer quantity;
    private BigDecimal price;
}
