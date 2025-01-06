package com.bersyte.services;

import com.bersyte.repositories.TaskLinkRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskLinkService {

    private TaskLinkRepository taskLinkRepository;

    public TaskLinkService(TaskLinkRepository taskLinkRepository) {
        this.taskLinkRepository = taskLinkRepository;
    }
}
