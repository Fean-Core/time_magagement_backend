package com.timemanagement.dto.task;

import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class UpdateTaskRequest {
    
    @Size(max = 255, message = "Título deve ter no máximo 255 caracteres")
    private String title;
    
    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String description;
    
    private String priority; // LOW, MEDIUM, HIGH
    
    private String status; // PENDING, IN_PROGRESS, COMPLETED
    
    @JsonProperty("due_date")
    private LocalDateTime dueDate;
    
    @JsonProperty("estimated_time")
    private Integer estimatedTime;
    
    @JsonProperty("category_id")
    private String categoryId;
    
    // Default constructor
    public UpdateTaskRequest() {}
    
    // Constructor with parameters
    public UpdateTaskRequest(String title, String description, String priority, String status, 
                           LocalDateTime dueDate, Integer estimatedTime, String categoryId) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
        this.estimatedTime = estimatedTime;
        this.categoryId = categoryId;
    }
    
    // Getters and setters
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    
    public Integer getEstimatedTime() {
        return estimatedTime;
    }
    
    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
    
    public String getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
