package com.bersyte.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entity representing a link associated with a task.
 */
@Entity(name = "task_links")
@Table
public class TaskLink extends PanacheEntity {
    /**
     * Unique identifier for the task link.
     */
    @Column(unique = true, nullable = false)
    private String taskLinkId;
    /**
     * URL of the task link.
     */
    private String  link;
    /**
     * Timestamp when the task link was created.
     */
    private LocalDateTime createdAt;

    /**
     * The task associated with this link.
     */
    @ManyToOne
    @JoinColumn(name = "taskOwnerId", nullable = false)
    private Task task;

     public String getTaskLinkId() {
          return taskLinkId;
     }

     public void setTaskLinkId(String taskLinkId) {
          this.taskLinkId = taskLinkId;
     }

     public String getLink() {
          return link;
     }

     public void setLink(String link) {
          this.link = link;
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
