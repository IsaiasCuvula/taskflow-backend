package com.bersyte.services;

import com.bersyte.repositories.SubtaskRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;

    public SubtaskService(SubtaskRepository subtaskRepository) {
        this.subtaskRepository = subtaskRepository;
    }
}
