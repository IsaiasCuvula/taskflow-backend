package com.bersyte.mappers;

import com.bersyte.dtos.taskLink.CreateTaskLinkDto;
import com.bersyte.dtos.taskLink.TaskLinkResponseDto;
import com.bersyte.entities.TaskLink;
import com.bersyte.exceptions.TaskNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Mapper class for converting between TaskLink entities and DTOs.
 */
@ApplicationScoped
public class TaskLinkMapper {

    /**
     * Converts a CreateTaskLinkDto to a TaskLink entity.
     *
     * @param dto the DTO containing task link creation details
     * @return a new TaskLink entity
     */
    public TaskLink fromDto(CreateTaskLinkDto dto){
        TaskLink taskLink = new TaskLink();
        taskLink.setTaskLinkId (dto.taskLinkId ());
        taskLink.setLink (dto.link ());
        taskLink.setCreatedAt (dto.createdAt ());
        return taskLink;
    }

    /**
     * Converts a TaskLink entity to a TaskLinkResponseDto.
     *
     * @param taskLink the TaskLink entity to convert
     * @return the corresponding TaskLinkResponseDto
     * @throws TaskNotFoundException if the provided task link is null
     */
    public TaskLinkResponseDto toResponseDto(TaskLink taskLink){
        if (taskLink == null) {
            throw new TaskNotFoundException ("Link cannot be null");
        }
        return new TaskLinkResponseDto (
                taskLink.getTaskLinkId (),
                taskLink.getTask ().getTaskId (),
                taskLink.getLink (),
                taskLink.getCreatedAt ()
        );
    }
}
