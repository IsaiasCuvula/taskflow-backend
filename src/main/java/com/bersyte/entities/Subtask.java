package com.bersyte.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "subtasks")
@Table
public class Subtask extends PanacheEntity {
   @Column(unique = true, nullable = false)
   private String subtaskId;
   private String  title;
   private Boolean isCompleted;
   private Long taskId;
   private LocalDateTime createdAt;

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

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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
