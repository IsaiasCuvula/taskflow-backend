package com.bersyte.dtos.taskLink;

import java.time.LocalDateTime;

public record TaskLinkResponseDto(
        String subtaskId,
        String taskId,
        String link,
        LocalDateTime createdAt
) {}
