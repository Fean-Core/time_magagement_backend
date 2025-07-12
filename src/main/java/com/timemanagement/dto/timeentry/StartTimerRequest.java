package com.timemanagement.dto.timeentry;

import jakarta.validation.constraints.NotBlank;

public class StartTimerRequest {
    
    @NotBlank(message = "ID da tarefa é obrigatório")
    private String taskId;
    
    private String description;
    
    // Default constructor
    public StartTimerRequest() {}
    
    // Constructor with parameters
    public StartTimerRequest(String taskId, String description) {
        this.taskId = taskId;
        this.description = description;
    }
    
    // Getters and setters
    public String getTaskId() {
        return taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}
