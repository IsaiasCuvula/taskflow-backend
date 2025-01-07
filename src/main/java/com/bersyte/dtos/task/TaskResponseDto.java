package com.bersyte.dtos.task;

import com.bersyte.dtos.subtask.SubtaskResponseDto;
import com.bersyte.dtos.taskLink.TaskLinkResponseDto;
import com.bersyte.utils.TaskPriority;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record TaskResponseDto(
       String taskId,
       String title,
       Boolean isCompleted,
       Double percentageCompleted,
       String description,
       LocalDate dueDate,
       LocalTime dueTime,
       TaskPriority priority,
       LocalDateTime createdAt,
       List<SubtaskResponseDto> subtasks,
       List<TaskLinkResponseDto> taskLinks
) {}
