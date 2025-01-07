package com.bersyte.entities;

import com.bersyte.utils.TaskPriority;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a task.
 */
@Entity(name = "tasks")
@Table
public class Task extends PanacheEntity {
   /**
    * Unique public identifier for the task.
    */
   @Column(unique = true, nullable = false)
   private String taskId;
   /**
    * Title of the task.
    */
   private String title;
   /**
    * Indicates whether the task is completed.
    */
   private Boolean isCompleted;
   /**
    * Percentage of completion for the task.
    */
   private Double percentageCompleted;
   /**
    * Description of the task.
    */
   private String description;
   /**
    * Due date for the task.
    */
   private LocalDate dueDate;
   /**
    * Due time for the task.
    */
   private LocalTime dueTime;
   /**
    * Priority level of the task.
    */
   private TaskPriority priority;
   /**
    * Timestamp when the task was created.
    */
   private LocalDateTime createdAt;

   /**
    * List of subtasks associated with this task.
    */
   @OneToMany(mappedBy = "task", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Subtask> subtasks = new ArrayList<> ();

   /**
    * List of links associated with this task.
    */
   @OneToMany(mappedBy = "task",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
   private List<TaskLink> taskLinks = new ArrayList<>();


   public String getTaskId() {
      return taskId;
   }

   public void setTaskId(String taskId) {
      this.taskId = taskId;
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

   public Double getPercentageCompleted() {
      return percentageCompleted;
   }

   public void setPercentageCompleted(Double percentageCompleted) {
      this.percentageCompleted = percentageCompleted;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public LocalDate getDueDate() {
      return dueDate;
   }

   public void setDueDate(LocalDate dueDate) {
      this.dueDate = dueDate;
   }

   public LocalTime getDueTime() {
      return dueTime;
   }

   public void setDueTime(LocalTime dueTime) {
      this.dueTime = dueTime;
   }

   public TaskPriority getPriority() {
      return priority;
   }

   public void setPriority(TaskPriority priority) {
      this.priority = priority;
   }

   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   public List<Subtask> getSubtasks() {
      return subtasks;
   }

   public void setSubtasks(List<Subtask> subtasks) {
      this.subtasks = subtasks;
   }

   public List<TaskLink> getTaskLinks() {
      return taskLinks;
   }

   public void setTaskLinks(List<TaskLink> taskLinks) {
      this.taskLinks = taskLinks;
   }
}
