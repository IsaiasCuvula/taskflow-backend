package com.bersyte.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entity representing a subtask of a task.
 */
@Entity(name = "subtasks")
@Table
public class Subtask extends PanacheEntity {
    /**
     * Unique identifier for the subtask.
     */
   @Column(unique = true, nullable = false)
   private String subtaskId;
   /**
    * Title of the subtask.
    */
   private String  title;
    /**
     * Indicates whether the subtask is completed.
     */
   private Boolean isCompleted;
    /**
     * Timestamp when the subtask was created.
     */
   private LocalDateTime createdAt;

    /**
     * The task associated with this subtask.
     */
    @ManyToOne
    @JoinColumn(name = "taskOwnerId", nullable = false)
    private Task task;

    public String getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(String subtaskId) {
        this.subtaskId = subtaskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
