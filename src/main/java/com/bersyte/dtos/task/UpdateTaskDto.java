package com.bersyte.dtos.task;

import com.bersyte.utils.TaskPriority;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateTaskDto(
        @NotEmpty(message = "Title cannot be empty")
        String title,
        @NotNull(message = "Description cannot be empty")
        String description,
        @NotNull(message = "Due date cannot be empty")
        LocalDate dueDate,
        @NotNull(message = "Due time cannot be empty")
        LocalTime dueTime,
        @NotNull(message = "Priority cannot be null")
        TaskPriority priority
) {}
