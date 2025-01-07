package com.bersyte.dtos.subtask;


import jakarta.validation.constraints.NotNull;

public record UpdateSubtaskDto(
        @NotNull(message = "Completion status cannot be null")
        Boolean isCompleted
) {}
