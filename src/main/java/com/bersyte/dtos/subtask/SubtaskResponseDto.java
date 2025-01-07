package com.bersyte.dtos.subtask;

import java.time.LocalDateTime;

public record SubtaskResponseDto(
        String subtaskId,
        String taskId,
        String title,
        Boolean isCompleted,
        LocalDateTime createdAt
) { }
