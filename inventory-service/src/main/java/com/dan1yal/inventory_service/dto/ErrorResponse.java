package com.dan1yal.inventory_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {
    private String message;
    private int httpStatusCode;
    private LocalDateTime timestamp;
}
