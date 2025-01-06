package com.bersyte.exceptions;

import java.io.Serializable;

public class TaskNotFoundException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public TaskNotFoundException(String message) {
        super(message);
    }
}