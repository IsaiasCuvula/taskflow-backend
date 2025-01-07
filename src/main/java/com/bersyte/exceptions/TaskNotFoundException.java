package com.bersyte.exceptions;

import java.io.Serializable;

/**
 * Custom exception for cases where a task is not found.
 */
public class TaskNotFoundException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new TaskNotFoundException with the specified message.
     *
     * @param message the detail message
     */
    public TaskNotFoundException(String message) {
        super(message);
    }
}