package com.bersyte.dtos.taskLink;

import com.bersyte.utils.AppHelper;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for creating a task link.
 *
 * @param taskLinkId Unique identifier for the task link
 * @param link       URL of the task link
 * @param createdAt  Timestamp when the task link was created
 */
public record CreateTaskLinkDto(
        String taskLinkId,
        @NotEmpty(message = "Link cannot be empty")
        String link,
        LocalDateTime createdAt
) {
    public CreateTaskLinkDto {
        createdAt = LocalDateTime.now();
        taskLinkId = AppHelper.getRandomId ();
    }
}
