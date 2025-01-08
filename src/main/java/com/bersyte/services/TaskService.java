package com.bersyte.services;

import com.bersyte.dtos.task.AllTaskResponseDto;
import com.bersyte.dtos.task.CreateTaskDto;
import com.bersyte.dtos.task.TaskResponseDto;
import com.bersyte.dtos.task.UpdateTaskDto;
import com.bersyte.entities.Subtask;
import com.bersyte.entities.Task;
import com.bersyte.exceptions.TaskNotFoundException;
import com.bersyte.mappers.TaskMapper;
import com.bersyte.repositories.TaskRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Service class for managing tasks.
 * Handles business logic and transactions for CRUD operations on tasks.
 */
@ApplicationScoped
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    /**
     * Constructor to initialize TaskService with dependencies.
     *
     * @param taskRepository repository for tasks
     * @param taskMapper     mapper for converting task objects
     */
    public TaskService(
            TaskRepository taskRepository,
            TaskMapper taskMapper
    ) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    /**
     * Marks a task as completed if all subtasks are completed.
     *
     * @param task the task to evaluate and update
     */
    @Transactional
    public void markTaskAsCompleted(Task task) {
        double percentageCompleted = percentageCompleted(task);
        task.setPercentageCompleted (percentageCompleted);

        if(percentageCompleted >= 100) {
            task.setCompleted (true);
        }
        taskRepository.persist (task);
    }

    /**
     * Calculates the completion percentage of a task based on its subtasks.
     *
     * @param task the task to evaluate
     * @return the percentage of completed subtasks
     */
    private double percentageCompleted(Task task) {
        List<Subtask> subtasks = task.getSubtasks();

        if(subtasks.isEmpty()){return 0.0;}

        long completedCount = subtasks.stream ()
                .filter (Subtask::getCompleted)
                .count ();

        return (completedCount * 100.0) / subtasks.size ();
    }

    /**
     * Updates an existing task by its ID.
     *
     * @param dto    the task update details
     * @param taskId the ID of the task to update
     * @return the updated task as a DTO
     */
    @Transactional
    public TaskResponseDto updateTask(UpdateTaskDto dto, String taskId){
        Task task = findTaskById (taskId);
        task.setDescription(dto.description ());
        task.setTitle (dto.title ());
        task.setDueDate (dto.dueDate ());
        task.setDueTime (dto.dueTime ());
        task.setDescription (dto.description ());
        task.setPriority (dto.priority ());
        //
        taskRepository.persist (task);
        //
        return taskMapper.toResponseDto (task);
    }

    /**
     * Deletes a task by its ID.
     *
     * @param taskId the ID of the task to delete
     * @return a confirmation message
     */
    @Transactional
    public String deleteTaskById(String taskId){
        Task task = findTaskById (taskId);
        taskRepository.delete (task);
        return "Task deleted successfully";
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param taskId the ID of the task to retrieve
     * @return the task as a DTO
     */
    public TaskResponseDto getTaskById(String taskId) {
        return taskMapper.toResponseDto (findTaskById (taskId));
    }

    /**
     * Retrieves a paginated list of all tasks.
     *
     * @param page     the page number
     * @param pageSize the number of tasks per page
     * @return a list of tasks as DTOs
     */
    public List<AllTaskResponseDto> getAllTasks(int page, int pageSize) {
        PanacheQuery<Task> tasks = taskRepository
                .findAll()
                .page (page, pageSize);
        return tasks.stream ().map (taskMapper::toAllTaskResponseDto).toList ();
    }

    /**
     * Creates a new task.
     *
     * @param dto the task creation details
     * @return the created task as a DTO
     */
    @Transactional
    public TaskResponseDto createTask(CreateTaskDto dto) {
        Task task = taskMapper.fromDto(dto);
        taskRepository.persist(task);
        return taskMapper.toResponseDto (task);
    }

    /**
     * Finds a task by its ID.
     *
     * @param taskId the ID of the task to find
     * @return the task entity
     * @throws TaskNotFoundException if the task is not found
     */
    public Task findTaskById(String taskId) {
        return taskRepository.find ("taskId", taskId)
                .stream()
                .findFirst ()
                .orElseThrow (() -> new TaskNotFoundException ("Task not found"));
    }

}
