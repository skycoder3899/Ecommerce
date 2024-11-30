package com.monk.commerce.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private Map<String, Object> additionalDetails;
    public ErrorResponse(int status, String error, String message, String path, Map<String, Object> additionalDetails) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.additionalDetails = additionalDetails;
    }
}
