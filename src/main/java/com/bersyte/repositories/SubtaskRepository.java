package com.bersyte.repositories;

import com.bersyte.entities.Subtask;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SubtaskRepository implements PanacheRepository<Subtask> {
}
