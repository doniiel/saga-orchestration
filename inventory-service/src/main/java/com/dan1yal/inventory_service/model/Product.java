package com.dan1yal.inventory_service.model;

import jakarta.annotation.Generated;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document("product")
public class Product {

    @Id
    private String id;
    private String name;
    private Integer quantity;
    private BigDecimal price;
}
