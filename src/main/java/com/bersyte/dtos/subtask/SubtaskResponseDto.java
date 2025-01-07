package com.bersyte.dtos.subtask;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for subtask response details.
 *
 * @param subtaskId  Unique identifier for the subtask
 * @param taskId     ID of the task associated with the subtask
 * @param title      Title of the subtask
 * @param isCompleted Indicates whether the subtask is completed
 * @param createdAt  Timestamp when the subtask was created
 */
public record SubtaskResponseDto(
        String subtaskId,
        String taskId,
        String title,
        Boolean isCompleted,
        LocalDateTime createdAt
) { }
