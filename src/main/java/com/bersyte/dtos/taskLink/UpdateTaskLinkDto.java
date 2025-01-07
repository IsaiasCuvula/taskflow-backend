package com.bersyte.dtos.taskLink;

/**
 * Data Transfer Object for updating a task link.
 *
 * @param link URL of the updated task link
 */
public record UpdateTaskLinkDto(
        String link
) {}
