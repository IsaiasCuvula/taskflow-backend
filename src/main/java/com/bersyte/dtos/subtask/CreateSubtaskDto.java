package com.bersyte.dtos.subtask;

import com.bersyte.utils.AppHelper;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for creating a subtask.
 *
 * @param subtaskId  Unique identifier for the subtask
 * @param title      Title of the subtask
 * @param isCompleted Indicates whether the subtask is completed
 * @param createdAt  Timestamp when the subtask was created
 */
public record CreateSubtaskDto(
        String subtaskId,
        @NotEmpty(message = "Title cannot be empty")
        String title,
        Boolean isCompleted,
        LocalDateTime createdAt
) {
    public CreateSubtaskDto {
        createdAt = LocalDateTime.now();
        isCompleted = false;
        subtaskId = AppHelper.getRandomId ();
    }
}