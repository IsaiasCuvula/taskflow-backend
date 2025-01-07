package com.bersyte.mappers;

import com.bersyte.dtos.task.CreateTaskDto;
import com.bersyte.dtos.task.TaskResponseDto;
import com.bersyte.entities.Task;
import com.bersyte.exceptions.TaskNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskMapper {

    private final SubtaskMapper subtaskMapper;
    private final TaskLinkMapper taskLinkMapper;

    public TaskMapper(SubtaskMapper subtaskMapper, TaskLinkMapper taskLinkMapper) {
        this.subtaskMapper = subtaskMapper;
        this.taskLinkMapper = taskLinkMapper;
    }

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
}
