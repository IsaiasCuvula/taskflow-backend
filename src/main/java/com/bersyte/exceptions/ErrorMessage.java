package com.bersyte.exceptions;

import java.time.LocalDateTime;

/**
 * Record representing a standardized error message for API responses.
 *
 * @param message    the error message
 * @param status     the HTTP status name
 * @param statusCode the HTTP status code
 * @param timestamp  the timestamp when the error occurred
 */
public record ErrorMessage(
        String message,
        String status,
        int statusCode,
        LocalDateTime timestamp
) {

    /**
     * Constructs an ErrorMessage with the current timestamp.
     *
     * @param message    the error message
     * @param status     the HTTP status name
     * @param statusCode the HTTP status code
     */
    public ErrorMessage(
            String message, String status, int statusCode
    ) {
        this(message,status,statusCode, LocalDateTime.now());
    }
}
