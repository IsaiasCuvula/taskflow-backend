package com.bersyte.entities;

import com.bersyte.utils.TaskPriority;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "tasks")
@Table
public class Task extends PanacheEntity {
   @Column(unique = true, nullable = false)
   private String taskId;
   private String title;
   private Boolean isCompleted;
   private Double percentageCompleted;
   private String description;
   private LocalDate dueDate;
   private LocalTime dueTime;
   private TaskPriority priority;
   private LocalDateTime createdAt;

   @OneToMany(mappedBy = "task", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Subtask> subtasks = new ArrayList<> ();

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
