package com.timemanagement.service;

import com.timemanagement.dto.timeentry.CreateTimeEntryRequest;
import com.timemanagement.dto.timeentry.UpdateTimeEntryRequest;
import com.timemanagement.dto.timeentry.StartTimerRequest;
import com.timemanagement.exception.ResourceNotFoundException;
import com.timemanagement.model.TimeEntry;
import com.timemanagement.repository.TimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TimeEntryService {
    
    @Autowired
    private TimeEntryRepository timeEntryRepository;
    
    public List<TimeEntry> findByUserId(String userId) {
        return timeEntryRepository.findByUserIdOrderByStartTimeDesc(userId);
    }
    
    public TimeEntry findByIdAndUserId(String id, String userId) {
        Optional<TimeEntry> timeEntry = timeEntryRepository.findById(id);
        if (timeEntry.isEmpty()) {
            throw new ResourceNotFoundException("Time entry not found with id: " + id);
        }
        if (!timeEntry.get().getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Time entry not found with id: " + id);
        }
        return timeEntry.get();
    }
    
    public TimeEntry createTimeEntry(CreateTimeEntryRequest request, String userId) {
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setTaskId(request.getTaskId());
        timeEntry.setUserId(userId);
        timeEntry.setDescription(request.getDescription());
        timeEntry.setStartTime(request.getStartTime() != null ? request.getStartTime() : LocalDateTime.now());
        
        if (request.getEndTime() != null) {
            timeEntry.setEndTime(request.getEndTime());
            // Calculate duration if both start and end times are provided
            long duration = Duration.between(timeEntry.getStartTime(), request.getEndTime()).getSeconds();
            timeEntry.setDuration(duration);
        }
        
        return timeEntryRepository.save(timeEntry);
    }
    
    public TimeEntry updateTimeEntry(String id, UpdateTimeEntryRequest request, String userId) {
        TimeEntry existingTimeEntry = findByIdAndUserId(id, userId);
        
        if (request.getDescription() != null) {
            existingTimeEntry.setDescription(request.getDescription());
        }
        if (request.getStartTime() != null) {
            existingTimeEntry.setStartTime(request.getStartTime());
        }
        if (request.getEndTime() != null) {
            existingTimeEntry.setEndTime(request.getEndTime());
        }
        
        // Recalculate duration if we have both start and end times
        if (existingTimeEntry.getStartTime() != null && existingTimeEntry.getEndTime() != null) {
            long duration = Duration.between(existingTimeEntry.getStartTime(), existingTimeEntry.getEndTime()).getSeconds();
            existingTimeEntry.setDuration(duration);
        }
        
        return timeEntryRepository.save(existingTimeEntry);
    }
    
    public void deleteTimeEntry(String id, String userId) {
        TimeEntry timeEntry = findByIdAndUserId(id, userId);
        timeEntryRepository.delete(timeEntry);
    }
    
    public TimeEntry startTimer(StartTimerRequest request, String userId) {
        // Check if there's already an active timer for this user
        List<TimeEntry> allEntries = timeEntryRepository.findByUserIdOrderByStartTimeDesc(userId);
        boolean hasActiveTimer = allEntries.stream()
            .anyMatch(entry -> entry.getEndTime() == null);
        
        if (hasActiveTimer) {
            throw new IllegalStateException("There is already an active timer running");
        }
        
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setTaskId(request.getTaskId());
        timeEntry.setUserId(userId);
        timeEntry.setDescription(request.getDescription());
        timeEntry.setStartTime(LocalDateTime.now());
        
        return timeEntryRepository.save(timeEntry);
    }
    
    public TimeEntry stopTimer(String timeEntryId, String userId) {
        TimeEntry timeEntry = findByIdAndUserId(timeEntryId, userId);
        
        if (timeEntry.getEndTime() != null) {
            throw new IllegalStateException("Timer is already stopped");
        }
        
        LocalDateTime endTime = LocalDateTime.now();
        timeEntry.setEndTime(endTime);
        
        // Calculate duration in seconds
        long duration = Duration.between(timeEntry.getStartTime(), endTime).getSeconds();
        timeEntry.setDuration(duration);
        
        return timeEntryRepository.save(timeEntry);
    }
    
    public TimeEntry findActiveTimeEntry(String userId) {
        try {
            List<TimeEntry> allEntries = timeEntryRepository.findByUserIdOrderByStartTimeDesc(userId);
            return allEntries.stream()
                .filter(entry -> entry.getEndTime() == null)
                .findFirst()
                .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding active time entry: " + e.getMessage(), e);
        }
    }
    
    public List<TimeEntry> findByTaskId(String taskId) {
        return timeEntryRepository.findByTaskId(taskId);
    }
    
    public List<TimeEntry> findByDateRange(String userId, LocalDateTime start, LocalDateTime end) {
        return timeEntryRepository.findByUserIdAndStartTimeBetween(userId, start, end);
    }
}
