package com.bersyte.dtos.task;

import com.bersyte.dtos.subtask.SubtaskResponseDto;
import com.bersyte.dtos.taskLink.TaskLinkResponseDto;
import com.bersyte.utils.TaskPriority;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Data Transfer Object for task response details.
 *
 * @param taskId             Unique identifier for the task
 * @param title              Title of the task
 * @param isCompleted        Indicates whether the task is completed
 * @param percentageCompleted Percentage of task completion
 * @param description        Description of the task
 * @param dueDate            Due date for the task
 * @param dueTime            Due time for the task
 * @param priority           Priority level of the task
 * @param createdAt          Timestamp when the task was created
 * @param subtasks           List of subtasks associated with the task
 * @param taskLinks          List of links associated with the task
 */
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
