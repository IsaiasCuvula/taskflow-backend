package com.bersyte.dtos.taskLink;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for task link response details.
 *
 * @param taskLinkId  ID of the subtask associated with the link
 * @param taskId     ID of the task associated with the link
 * @param link       URL of the task link
 * @param createdAt  Timestamp when the task link was created
 */
public record TaskLinkResponseDto(
        String taskLinkId,
        String taskId,
        String link,
        LocalDateTime createdAt
) {}
