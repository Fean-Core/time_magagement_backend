package com.timemanagement.controller;

import com.timemanagement.dto.task.CreateTaskRequest;
import com.timemanagement.dto.task.UpdateTaskRequest;
import com.timemanagement.model.Task;
import com.timemanagement.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    @GetMapping
    public ResponseEntity<List<Task>> getTasks(Authentication auth) {
        String userEmail = auth.getName();
        List<Task> tasks = taskService.findByUserId(userEmail);
        return ResponseEntity.ok(tasks);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable String id, Authentication auth) {
        String userEmail = auth.getName();
        Task task = taskService.findByIdAndUserId(id, userEmail);
        return ResponseEntity.ok(task);
    }
    
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody CreateTaskRequest request, Authentication auth) {
        String userEmail = auth.getName();
        Task task = taskService.createTask(request, userEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @Valid @RequestBody UpdateTaskRequest request, Authentication auth) {
        String userEmail = auth.getName();
        Task updatedTask = taskService.updateTask(id, request, userEmail);
        return ResponseEntity.ok(updatedTask);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id, Authentication auth) {
        String userEmail = auth.getName();
        taskService.deleteTask(id, userEmail);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable String id, Authentication auth) {
        String userEmail = auth.getName();
        Task completedTask = taskService.completeTask(id, userEmail);
        return ResponseEntity.ok(completedTask);
    }
    
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Task> toggleTaskStatus(@PathVariable String id, Authentication auth) {
        String userEmail = auth.getName();
        Task toggledTask = taskService.toggleTaskStatus(id, userEmail);
        return ResponseEntity.ok(toggledTask);
    }
    
    @GetMapping("/overdue")
    public ResponseEntity<List<Task>> getOverdueTasks(Authentication auth) {
        String userEmail = auth.getName();
        List<Task> overdueTasks = taskService.findOverdueTasksByUserId(userEmail);
        return ResponseEntity.ok(overdueTasks);
    }
    
    @GetMapping("/today")
    public ResponseEntity<List<Task>> getTasksDueToday(Authentication auth) {
        String userEmail = auth.getName();
        List<Task> todayTasks = taskService.findTasksDueTodayByUserId(userEmail);
        return ResponseEntity.ok(todayTasks);
    }
}
