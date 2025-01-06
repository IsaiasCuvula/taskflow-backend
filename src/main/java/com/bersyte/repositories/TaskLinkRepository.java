package com.bersyte.repositories;

import com.bersyte.entities.TaskLink;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskLinkRepository implements PanacheRepository<TaskLink> {
}
