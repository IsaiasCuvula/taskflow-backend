package com.bersyte.repositories;

import com.bersyte.entities.Task;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class TaskRepository implements PanacheRepository<Task> { }
