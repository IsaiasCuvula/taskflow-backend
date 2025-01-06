package com.bersyte.services;

import com.bersyte.repositories.TaskRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
}
