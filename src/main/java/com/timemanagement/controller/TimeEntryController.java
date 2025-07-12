package com.timemanagement.controller;

import com.timemanagement.dto.timeentry.CreateTimeEntryRequest;
import com.timemanagement.dto.timeentry.UpdateTimeEntryRequest;
import com.timemanagement.dto.timeentry.StartTimerRequest;
import com.timemanagement.model.TimeEntry;
import com.timemanagement.service.TimeEntryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-entries")
@CrossOrigin(origins = "*")
public class TimeEntryController {
    
    @Autowired
    private TimeEntryService timeEntryService;
    
    @GetMapping
    public ResponseEntity<List<TimeEntry>> getTimeEntries(Authentication auth) {
        String userEmail = auth.getName();
        List<TimeEntry> timeEntries = timeEntryService.findByUserId(userEmail);
        return ResponseEntity.ok(timeEntries);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> getTimeEntry(@PathVariable String id, Authentication auth) {
        String userEmail = auth.getName();
        TimeEntry timeEntry = timeEntryService.findByIdAndUserId(id, userEmail);
        return ResponseEntity.ok(timeEntry);
    }
    
    @PostMapping
    public ResponseEntity<TimeEntry> createTimeEntry(@Valid @RequestBody CreateTimeEntryRequest request, Authentication auth) {
        String userEmail = auth.getName();
        TimeEntry timeEntry = timeEntryService.createTimeEntry(request, userEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(timeEntry);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TimeEntry> updateTimeEntry(@PathVariable String id, @Valid @RequestBody UpdateTimeEntryRequest request, Authentication auth) {
        String userEmail = auth.getName();
        TimeEntry updatedTimeEntry = timeEntryService.updateTimeEntry(id, request, userEmail);
        return ResponseEntity.ok(updatedTimeEntry);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeEntry(@PathVariable String id, Authentication auth) {
        String userEmail = auth.getName();
        timeEntryService.deleteTimeEntry(id, userEmail);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/start")
    public ResponseEntity<TimeEntry> startTimer(@Valid @RequestBody StartTimerRequest request, Authentication auth) {
        String userEmail = auth.getName();
        TimeEntry timeEntry = timeEntryService.startTimer(request, userEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(timeEntry);
    }
    
    @PatchMapping("/stop/{id}")
    public ResponseEntity<TimeEntry> stopTimer(@PathVariable String id, Authentication auth) {
        try {
            System.out.println("DEBUG: Stopping timer for ID: " + id);
            String userEmail = auth.getName();
            System.out.println("DEBUG: User email: " + userEmail);
            
            TimeEntry timeEntry = timeEntryService.stopTimer(id, userEmail);
            System.out.println("DEBUG: Timer stopped successfully");
            return ResponseEntity.ok(timeEntry);
        } catch (Exception e) {
            System.err.println("ERROR in stopTimer: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping("/{id}/stop")
    public ResponseEntity<TimeEntry> stopTimerPost(@PathVariable String id, Authentication auth) {
        try {
            System.out.println("DEBUG: Stopping timer (POST) for ID: " + id);
            String userEmail = auth.getName();
            System.out.println("DEBUG: User email: " + userEmail);
            
            TimeEntry timeEntry = timeEntryService.stopTimer(id, userEmail);
            System.out.println("DEBUG: Timer stopped successfully (POST)");
            return ResponseEntity.ok(timeEntry);
        } catch (Exception e) {
            System.err.println("ERROR in stopTimerPost: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PatchMapping("/stop")
    public ResponseEntity<TimeEntry> stopActiveTimer(Authentication auth) {
        try {
            String userEmail = auth.getName();
            System.out.println("DEBUG: Stopping active timer for user: " + userEmail);
            
            // First find the active timer
            TimeEntry activeEntry = timeEntryService.findActiveTimeEntry(userEmail);
            if (activeEntry == null) {
                System.out.println("DEBUG: No active timer found");
                return ResponseEntity.badRequest().build(); // No active timer
            }
            
            System.out.println("DEBUG: Found active timer ID: " + activeEntry.getId());
            TimeEntry stoppedEntry = timeEntryService.stopTimer(activeEntry.getId(), userEmail);
            System.out.println("DEBUG: Active timer stopped successfully");
            return ResponseEntity.ok(stoppedEntry);
        } catch (Exception e) {
            System.err.println("ERROR in stopActiveTimer: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/active")
    public ResponseEntity<TimeEntry> getActiveTimeEntry(Authentication auth) {
        try {
            String userEmail = auth.getName();
            System.out.println("DEBUG: Getting active time entry for user: " + userEmail);
            
            // Get all time entries for user and filter active ones manually
            List<TimeEntry> allEntries = timeEntryService.findByUserId(userEmail);
            TimeEntry activeEntry = allEntries.stream()
                .filter(entry -> entry.getEndTime() == null)
                .findFirst()
                .orElse(null);
            
            System.out.println("DEBUG: Found active entry: " + (activeEntry != null ? activeEntry.getId() : "none"));
            
            if (activeEntry != null) {
                return ResponseEntity.ok(activeEntry);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            System.err.println("ERROR in getActiveTimeEntry: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
