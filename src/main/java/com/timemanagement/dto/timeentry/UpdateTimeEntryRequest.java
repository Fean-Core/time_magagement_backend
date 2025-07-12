package com.timemanagement.dto.timeentry;

import java.time.LocalDateTime;

public class UpdateTimeEntryRequest {
    
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    private String description;
    
    // Default constructor
    public UpdateTimeEntryRequest() {}
    
    // Constructor with parameters
    public UpdateTimeEntryRequest(LocalDateTime startTime, LocalDateTime endTime, String description) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }
    
    // Getters and setters
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
