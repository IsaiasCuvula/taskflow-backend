package com.bersyte.repositories;

import com.bersyte.entities.Task;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class TaskRepository implements PanacheRepository<Task> {

   public Optional<Task> findByTaskId(String taskId) {
       return find("taskId", taskId).firstResultOptional();
   }
}
