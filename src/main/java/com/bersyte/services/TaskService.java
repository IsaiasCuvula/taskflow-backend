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

@ApplicationScoped
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;


    public TaskService(
            TaskRepository taskRepository,
            TaskMapper taskMapper
    ) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Transactional
    public void markTaskAsCompleted(Task task) {
        double percentageCompleted = percentageCompleted(task);
        task.setPercentageCompleted (percentageCompleted);

        if(percentageCompleted >= 100) {
            task.setCompleted (true);
        }
        taskRepository.persist (task);
    }

    private double percentageCompleted(Task task) {
        List<Subtask> subtasks = task.getSubtasks();

        if(subtasks.isEmpty()){return 0.0;}

        long completedCount = subtasks.stream ()
                .filter (Subtask::getCompleted)
                .count ();

        return (completedCount * 100.0) / subtasks.size ();
    }

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

    @Transactional
    public String deleteTaskById(String taskId){
        Task task = findTaskById (taskId);
        taskRepository.delete (task);
        return "Task deleted successfully";
    }

    public TaskResponseDto getTaskById(String taskId) {
        return taskMapper.toResponseDto (findTaskById (taskId));
    }


    public List<AllTaskResponseDto> getAllTasks(int page, int pageSize) {
        PanacheQuery<Task> tasks = taskRepository
                .findAll()
                .page (page, pageSize);
        return tasks.stream ().map (taskMapper::toAllTaskResponseDto).toList ();
    }

    @Transactional
    public TaskResponseDto createTask(CreateTaskDto dto) {
        Task task = taskMapper.fromDto(dto);
        taskRepository.persist(task);
        return taskMapper.toResponseDto (task);
    }

    Task findTaskById(String taskId) {
        return taskRepository.find ("taskId", taskId)
                .stream()
                .findFirst ()
                .orElseThrow (() -> new TaskNotFoundException ("Task not found"));
    }

}
