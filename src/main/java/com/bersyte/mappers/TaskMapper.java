package com.bersyte.mappers;

import com.bersyte.dtos.task.AllTaskResponseDto;
import com.bersyte.dtos.task.CreateTaskDto;
import com.bersyte.dtos.task.TaskResponseDto;
import com.bersyte.entities.Task;
import com.bersyte.exceptions.TaskNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Mapper class for converting between Task entities and DTOs.
 */
@ApplicationScoped
public class TaskMapper {

    private final SubtaskMapper subtaskMapper;
    private final TaskLinkMapper taskLinkMapper;

    /**
     * Constructor to initialize TaskMapper with dependencies.
     *
     * @param subtaskMapper  mapper for converting subtask objects
     * @param taskLinkMapper mapper for converting task link objects
     */
    public TaskMapper(SubtaskMapper subtaskMapper, TaskLinkMapper taskLinkMapper) {
        this.subtaskMapper = subtaskMapper;
        this.taskLinkMapper = taskLinkMapper;
    }

    /**
     * Converts a CreateTaskDto to a Task entity.
     *
     * @param dto the DTO containing task creation details
     * @return a new Task entity
     */
    public Task fromDto(CreateTaskDto dto){
        Task task = new Task();
        task.setTaskId (dto.taskId ());
        task.setTitle (dto.title ());
        task.setCompleted (dto.isCompleted ());
        task.setPercentageCompleted (dto.percentageCompleted ());
        task.setDescription (dto.description ());
        task.setDueDate (dto.dueDate ());
        task.setDueTime (dto.dueTime ());
        task.setPriority (dto.priority ());
        task.setCreatedAt (dto.createdAt ());
        return task;
    }

    /**
     * Converts a Task entity to a TaskResponseDto.
     *
     * @param task the Task entity to convert
     * @return the corresponding TaskResponseDto
     * @throws TaskNotFoundException if the provided task is null
     */
    public TaskResponseDto toResponseDto(Task task){
        if (task == null) {
            throw new TaskNotFoundException ("Task not found");
        }
        return new TaskResponseDto (
                task.getTaskId (),
                task.getTitle (),
                task.getCompleted (),
                task.getPercentageCompleted (),
                task.getDescription (),
                task.getDueDate (),
                task.getDueTime (),
                task.getPriority (),
                task.getCreatedAt (),
                task.getSubtasks ().stream ().map (subtaskMapper::toResponseDto).toList (),
                task.getTaskLinks ().stream ().map (taskLinkMapper::toResponseDto).toList ()
        );
    }

    /**
     * Converts a Task entity to an AllTaskResponseDto.
     *
     * @param task the Task entity to convert
     * @return the corresponding AllTaskResponseDto
     * @throws TaskNotFoundException if the provided task is null
     */
    public AllTaskResponseDto toAllTaskResponseDto(Task task){
        if (task == null) {
            throw new TaskNotFoundException ("Task not found");
        }
        return new AllTaskResponseDto (
                task.getTaskId (),
                task.getTitle (),
                task.getCompleted (),
                task.getPercentageCompleted (),
                task.getDescription (),
                task.getDueDate (),
                task.getDueTime (),
                task.getPriority (),
                task.getCreatedAt ()
        );
    }
}
