package com.bersyte.services;

import com.bersyte.dtos.CreateTaskDto;
import com.bersyte.dtos.TaskResponseDto;
import com.bersyte.entities.Task;
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

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }


    public List<TaskResponseDto> getAllTasks(int page, int pageSize) {
        PanacheQuery<Task> recipes = taskRepository
                .findAll()
                .page (page, pageSize);
        return recipes.stream ().map (taskMapper::toResponseDto).toList ();
    }

    @Transactional
    public TaskResponseDto createTask(CreateTaskDto dto) {
        Task task = taskMapper.fromDto(dto);
        taskRepository.persist(task);
        return taskMapper.toResponseDto (task);
    }

}
