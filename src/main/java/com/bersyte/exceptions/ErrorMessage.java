package com.bersyte.exceptions;

import java.time.LocalDateTime;

public record ErrorMessage(
        String message,
        String status,
        int statusCode,
        LocalDateTime timestamp
) {
    public ErrorMessage(
            String message, String status, int statusCode
    ) {
        this(message,status,statusCode, LocalDateTime.now());
    }
}
