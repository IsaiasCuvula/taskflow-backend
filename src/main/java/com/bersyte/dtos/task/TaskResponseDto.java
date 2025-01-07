package com.bersyte.dtos.task;

import com.bersyte.utils.TaskPriority;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record TaskResponseDto(
       String taskId,
       String title,
       Boolean isCompleted,
       Double percentageCompleted,
       String description,
       LocalDate dueDate,
       LocalTime dueTime,
       TaskPriority priority,
       LocalDateTime createdAt
//       List<Subtask> subtasks,
//       List<TaskLink> taskLinks
) {}
