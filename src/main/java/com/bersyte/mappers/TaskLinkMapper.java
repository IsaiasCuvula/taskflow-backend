package com.bersyte.mappers;

import com.bersyte.dtos.taskLink.CreateTaskLinkDto;
import com.bersyte.dtos.taskLink.TaskLinkResponseDto;
import com.bersyte.entities.TaskLink;
import com.bersyte.exceptions.TaskNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskLinkMapper {

    public TaskLink fromDto(CreateTaskLinkDto dto){
        TaskLink taskLink = new TaskLink();
        taskLink.setTaskLinkId (dto.taskLinkId ());
        taskLink.setLink (dto.link ());
        taskLink.setCreatedAt (dto.createdAt ());
        return taskLink;
    }

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
