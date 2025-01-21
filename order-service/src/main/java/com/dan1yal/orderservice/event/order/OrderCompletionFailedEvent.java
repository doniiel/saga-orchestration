package com.dan1yal.orderservice.event.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCompletionFailedEvent {
    private String orderId;
}
