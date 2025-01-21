package com.dan1yal.orderservice.command.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelOrderCommand {
    private Long orderId;
}
