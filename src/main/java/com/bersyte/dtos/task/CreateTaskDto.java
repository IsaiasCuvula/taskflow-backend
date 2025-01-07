package com.bersyte.dtos.task;

import com.bersyte.utils.AppHelper;
import com.bersyte.utils.TaskPriority;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Data Transfer Object for creating a task.
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
 */
public record CreateTaskDto(
   String taskId,
   @NotEmpty(message = "Title cannot be empty")
   String title,
   Boolean isCompleted,
   Double percentageCompleted,
   String description,
   @NotNull(message = "Due date cannot be empty")
   LocalDate dueDate,
   @NotNull(message = "Due time cannot be empty")
   LocalTime dueTime,
   @NotNull(message = "Priority cannot be null")
   TaskPriority priority,
   LocalDateTime createdAt
) {
    public CreateTaskDto {
        createdAt = LocalDateTime.now();
        isCompleted = false;
        taskId = AppHelper.getRandomId ();
        percentageCompleted = 0.0;
    }
}
