package com.bersyte.mappers;

import com.bersyte.dtos.subtask.CreateSubtaskDto;
import com.bersyte.dtos.subtask.SubtaskResponseDto;
import com.bersyte.entities.Subtask;
import com.bersyte.exceptions.TaskNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Mapper class for converting between Subtask entities and DTOs.
 */
@ApplicationScoped
public class SubtaskMapper {

    /**
     * Converts a CreateSubtaskDto to a Subtask entity.
     *
     * @param dto the DTO containing subtask creation details
     * @return a new Subtask entity
     */
    public Subtask fromDto(CreateSubtaskDto dto){
        Subtask subtask = new Subtask();
        subtask.setSubtaskId (dto.subtaskId ());
        subtask.setTitle (dto.title ());
        subtask.setCreatedAt (dto.createdAt ());
        subtask.setCompleted (dto.isCompleted ());
        return subtask;
    }

    /**
     * Converts a Subtask entity to a SubtaskResponseDto.
     *
     * @param subtask the Subtask entity to convert
     * @return the corresponding SubtaskResponseDto
     * @throws TaskNotFoundException if the provided subtask is null
     */
    public SubtaskResponseDto toResponseDto(Subtask subtask){
        if (subtask == null) {
            throw new TaskNotFoundException ("Subtask cannot be null");
        }
        return new SubtaskResponseDto (
                subtask.getSubtaskId (),
                subtask.getTask ().getTaskId (),
                subtask.getTitle (),
                subtask.getCompleted (),
                subtask.getCreatedAt ()
        );
    }
}
