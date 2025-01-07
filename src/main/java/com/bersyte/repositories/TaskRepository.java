package com.bersyte.repositories;

import com.bersyte.entities.Task;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository interface for managing Task entities.
 */
@ApplicationScoped
public class TaskRepository implements PanacheRepository<Task> { }
