package com.dan1yal.inventory_service.model;

import lombok.*;
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

    private String id;
    private String name;
    private String quantity;
    private BigDecimal price;
}
