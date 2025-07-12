package com.timemanagement.dto.timeentry;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CreateTimeEntryRequest {
    
    @NotBlank(message = "ID da tarefa é obrigatório")
    private String taskId;
    
    @NotNull(message = "Hora de início é obrigatória")
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    private String description;
    
    // Default constructor
    public CreateTimeEntryRequest() {}
    
    // Constructor with parameters
    public CreateTimeEntryRequest(String taskId, LocalDateTime startTime, LocalDateTime endTime, String description) {
        this.taskId = taskId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }
    
    // Getters and setters
    public String getTaskId() {
        return taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}
