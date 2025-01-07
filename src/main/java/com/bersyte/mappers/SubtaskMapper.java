package com.bersyte.mappers;

import com.bersyte.dtos.subtask.CreateSubtaskDto;
import com.bersyte.dtos.subtask.SubtaskResponseDto;
import com.bersyte.entities.Subtask;
import com.bersyte.exceptions.TaskNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class SubtaskMapper {

    public Subtask fromDto(CreateSubtaskDto dto){
        Subtask subtask = new Subtask();
        subtask.setSubtaskId (dto.subtaskId ());
        subtask.setTitle (dto.title ());
        subtask.setCreatedAt (dto.createdAt ());
        subtask.setCompleted (dto.isCompleted ());
        return subtask;
    }

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
