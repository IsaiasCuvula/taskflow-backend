package com.bersyte.dtos.taskLink;

import com.bersyte.utils.AppHelper;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

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
