package com.bersyte.dtos.subtask;

import com.bersyte.utils.AppHelper;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

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