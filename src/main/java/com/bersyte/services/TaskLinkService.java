package com.bersyte.services;

import com.bersyte.dtos.taskLink.CreateTaskLinkDto;
import com.bersyte.dtos.taskLink.TaskLinkResponseDto;
import com.bersyte.dtos.taskLink.UpdateTaskLinkDto;
import com.bersyte.entities.Task;
import com.bersyte.entities.TaskLink;
import com.bersyte.exceptions.TaskNotFoundException;
import com.bersyte.mappers.TaskLinkMapper;
import com.bersyte.repositories.TaskLinkRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TaskLinkService {

    private final TaskLinkRepository taskLinkRepository;
    private final TaskService taskService;
    private final TaskLinkMapper taskLinkMapper;

    public TaskLinkService(
            TaskLinkRepository taskLinkRepository,
            TaskService taskService,
            TaskLinkMapper taskLinkMapper
    ) {
        this.taskLinkRepository = taskLinkRepository;
        this.taskService = taskService;
        this.taskLinkMapper = taskLinkMapper;
    }

    @Transactional
    public TaskLinkResponseDto updateTaskLink(UpdateTaskLinkDto dto, String taskLinkId){
        TaskLink taskLink = findTaskLinkById (taskLinkId);
        taskLink.setLink (dto.link ());
        //
        taskLinkRepository.persist (taskLink);
        //
        return taskLinkMapper.toResponseDto (taskLink);
    }

    @Transactional
    public String deleteTaskLinkById(String taskLinkId){
        TaskLink taskLink = findTaskLinkById (taskLinkId);
        taskLinkRepository.delete (taskLink);
        return "Link deleted successfully";
    }

    public TaskLinkResponseDto getTaskLinkById(String taskLinkId) {
        return taskLinkMapper.toResponseDto (findTaskLinkById (taskLinkId));
    }

    @Transactional
    public TaskLinkResponseDto createTaskLink(CreateTaskLinkDto dto, String taskLinkId) {
        Task task = this.findTaskById(taskLinkId);

        TaskLink taskLink = taskLinkMapper.fromDto(dto);
        taskLink.setTask (task);
        taskLinkRepository.persist(taskLink);
        return taskLinkMapper.toResponseDto (taskLink);
    }

    private TaskLink findTaskLinkById(String taskLinkId) {
        return taskLinkRepository.find ("taskLinkId", taskLinkId)
                .stream()
                .findFirst ()
                .orElseThrow (() -> new TaskNotFoundException ("Task link not found"));
    }

    private Task findTaskById(String taskId) {
        return taskService.findTaskById (taskId);
    }
}
