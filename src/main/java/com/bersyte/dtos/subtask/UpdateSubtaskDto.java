package com.bersyte.dtos.subtask;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for updating a subtask.
 *
 * @param isCompleted Indicates whether the subtask is completed
 * @param subtaskId   Unique identifier for the subtask
 */
public record UpdateSubtaskDto(
        @NotNull(message = "Completion status cannot be null")
        Boolean isCompleted,
        @NotEmpty(message = "Subtask id cannot be empty")
        String subtaskId
) {}
