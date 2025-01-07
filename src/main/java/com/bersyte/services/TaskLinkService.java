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

/**
 * Service class for managing task links.
 * Handles business logic and transactions for CRUD operations on task links.
 */
@ApplicationScoped
public class TaskLinkService {

    private final TaskLinkRepository taskLinkRepository;
    private final TaskService taskService;
    private final TaskLinkMapper taskLinkMapper;

    /**
     * Constructor to initialize TaskLinkService with dependencies.
     *
     * @param taskLinkRepository repository for task links
     * @param taskService        service for managing tasks
     * @param taskLinkMapper     mapper for converting task link objects
     */
    public TaskLinkService(
            TaskLinkRepository taskLinkRepository,
            TaskService taskService,
            TaskLinkMapper taskLinkMapper
    ) {
        this.taskLinkRepository = taskLinkRepository;
        this.taskService = taskService;
        this.taskLinkMapper = taskLinkMapper;
    }


    /**
     * Updates an existing task link by its ID.
     *
     * @param dto         the task link update details
     * @param taskLinkId  the ID of the task link to update
     * @return the updated task link as a DTO
     */
    @Transactional
    public TaskLinkResponseDto updateTaskLink(UpdateTaskLinkDto dto, String taskLinkId){
        TaskLink taskLink = findTaskLinkById (taskLinkId);
        taskLink.setLink (dto.link ());
        //
        taskLinkRepository.persist (taskLink);
        //
        return taskLinkMapper.toResponseDto (taskLink);
    }

    /**
     * Deletes a task link by its ID.
     *
     * @param taskLinkId the ID of the task link to delete
     * @return a confirmation message
     */
    @Transactional
    public String deleteTaskLinkById(String taskLinkId){
        TaskLink taskLink = findTaskLinkById (taskLinkId);
        taskLinkRepository.delete (taskLink);
        return "Link deleted successfully";
    }

    /**
     * Retrieves a task link by its ID.
     *
     * @param taskLinkId the ID of the task link to retrieve
     * @return the task link as a DTO
     */
    public TaskLinkResponseDto getTaskLinkById(String taskLinkId) {
        return taskLinkMapper.toResponseDto (findTaskLinkById (taskLinkId));
    }

    /**
     * Creates a new task link associated with a specific task.
     *
     * @param dto         the task link creation details
     * @param taskLinkId  the ID of the associated task
     * @return the created task link as a DTO
     */
    @Transactional
    public TaskLinkResponseDto createTaskLink(CreateTaskLinkDto dto, String taskLinkId) {
        Task task = this.findTaskById(taskLinkId);

        TaskLink taskLink = taskLinkMapper.fromDto(dto);
        taskLink.setTask (task);
        taskLinkRepository.persist(taskLink);
        return taskLinkMapper.toResponseDto (taskLink);
    }

    /**
     * Finds a task link by its ID.
     *
     * @param taskLinkId the ID of the task link to find
     * @return the task link entity
     * @throws TaskNotFoundException if the task link is not found
     */
    private TaskLink findTaskLinkById(String taskLinkId) {
        return taskLinkRepository.find ("taskLinkId", taskLinkId)
                .stream()
                .findFirst ()
                .orElseThrow (() -> new TaskNotFoundException ("Task link not found"));
    }

    /**
     * Finds a task by its ID.
     *
     * @param taskId the ID of the task to find
     * @return the task entity
     * @throws TaskNotFoundException if the task is not found
     */
    private Task findTaskById(String taskId) {
        return taskService.findTaskById (taskId);
    }
}
