package com.bersyte.services;

import com.bersyte.dtos.subtask.CreateSubtaskDto;
import com.bersyte.dtos.subtask.SubtaskResponseDto;
import com.bersyte.dtos.subtask.UpdateSubtaskDto;
import com.bersyte.entities.Subtask;
import com.bersyte.entities.Task;
import com.bersyte.exceptions.TaskNotFoundException;
import com.bersyte.mappers.SubtaskMapper;
import com.bersyte.repositories.SubtaskRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Objects;

/**
 * Service class for managing subtasks.
 * Handles the business logic for CRUD operations on subtasks.
 */
@ApplicationScoped
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;
    private final SubtaskMapper subtaskMapper;
    private  final TaskService taskService;

    /**
     * Constructor to initialize SubtaskService with dependencies.
     *
     * @param subtaskRepository the repository managing subtask persistence
     * @param subtaskMapper     the mapper for converting between DTOs and entities
     * @param taskService       the service managing tasks
     */
    public SubtaskService(
            SubtaskRepository subtaskRepository,
            SubtaskMapper subtaskMapper, TaskService taskService
    ) {
        this.subtaskRepository = subtaskRepository;
        this.subtaskMapper = subtaskMapper;
        this.taskService = taskService;
    }

    /**
     * Updates an existing subtask and its completion status.
     *
     * @param dto    the subtask update details
     * @param taskId the ID of the associated task
     * @return the updated subtask as a DTO
     */
    @Transactional
    public SubtaskResponseDto updateSubtask(UpdateSubtaskDto dto, String taskId){
        Task task = taskService.findTaskById(taskId);
        Subtask subtask = findSubtaskById (dto.subtaskId());

        if(!Objects.equals (subtask.getTask ().getTaskId (), task.getTaskId ())){
          throw new TaskNotFoundException ("Subtask not found");
        }

        subtask.setCompleted (dto.isCompleted());
        //
        subtaskRepository.persist (subtask);
        //
        taskService.markTaskAsCompleted (task);

        return subtaskMapper.toResponseDto (subtask);
    }

    /**
     * Deletes a subtask by its ID.
     *
     * @param taskId the ID of the subtask to delete
     * @return a confirmation message
     */
    @Transactional
    public String deleteSubtaskById(String taskId){
        Subtask subtask = findSubtaskById (taskId);
        subtaskRepository.delete (subtask);
        return "Subtask deleted successfully";
    }

    /**
     * Retrieves a subtask by its ID.
     *
     * @param subtaskId the ID of the subtask to retrieve
     * @return the subtask as a DTO
     */
    public SubtaskResponseDto getSubtaskById(String subtaskId) {
        return subtaskMapper.toResponseDto (findSubtaskById (subtaskId));
    }

    /**
     * Creates a new subtask associated with a specific task.
     *
     * @param dto    the subtask creation details
     * @param taskId the ID of the associated task
     * @return the created subtask as a DTO
     */
    @Transactional
    public SubtaskResponseDto createSubtask(CreateSubtaskDto dto, String taskId) {
        Task task = this.findTaskById(taskId);

        Subtask subtask = subtaskMapper.fromDto(dto);
        subtask.setTask (task);
        subtaskRepository.persist(subtask);
        return subtaskMapper.toResponseDto (subtask);
    }

    /**
     * Finds a subtask by its ID.
     *
     * @param subtaskId the ID of the subtask to find
     * @return the found subtask
     */
    private Subtask findSubtaskById(String subtaskId) {
        return subtaskRepository.find ("subtaskId", subtaskId)
                .stream()
                .findFirst ()
                .orElseThrow (() -> new TaskNotFoundException ("Subtask not found"));
    }

    /**
     * Finds a task by its ID using TaskService.
     *
     * @param taskId the ID of the task to find
     * @return the found task
     */
    private Task findTaskById(String taskId) {
        return taskService.findTaskById (taskId);
    }
}
