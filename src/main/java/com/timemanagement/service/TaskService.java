package com.timemanagement.service;

import com.timemanagement.dto.task.CreateTaskRequest;
import com.timemanagement.dto.task.UpdateTaskRequest;
import com.timemanagement.exception.ResourceNotFoundException;
import com.timemanagement.model.Task;
import com.timemanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    public List<Task> findByUserId(String userId) {
        return taskRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    
    public Task findByIdAndUserId(String id, String userId) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        if (!task.get().getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        return task.get();
    }
    
    public Task createTask(CreateTaskRequest request, String userId) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(Task.Priority.valueOf(request.getPriority()));
        task.setStatus(Task.Status.PENDING);
        task.setDueDate(request.getDueDate());
        task.setEstimatedTime(request.getEstimatedTime());
        task.setCategoryId(request.getCategoryId());
        task.setUserId(userId);
        return taskRepository.save(task);
    }
    
    public Task updateTask(String id, UpdateTaskRequest request, String userId) {
        Task existingTask = findByIdAndUserId(id, userId);
        
        if (request.getTitle() != null) {
            existingTask.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            existingTask.setDescription(request.getDescription());
        }
        if (request.getPriority() != null) {
            existingTask.setPriority(Task.Priority.valueOf(request.getPriority()));
        }
        if (request.getStatus() != null) {
            existingTask.setStatus(Task.Status.valueOf(request.getStatus()));
        }
        if (request.getDueDate() != null) {
            existingTask.setDueDate(request.getDueDate());
        }
        if (request.getEstimatedTime() != null) {
            existingTask.setEstimatedTime(request.getEstimatedTime());
        }
        if (request.getCategoryId() != null) {
            existingTask.setCategoryId(request.getCategoryId());
        }
        
        return taskRepository.save(existingTask);
    }
    
    public void deleteTask(String id, String userId) {
        Task task = findByIdAndUserId(id, userId);
        taskRepository.delete(task);
    }
    
    public Task completeTask(String id, String userId) {
        Task task = findByIdAndUserId(id, userId);
        task.setStatus(Task.Status.COMPLETED);
        return taskRepository.save(task);
    }
    
    public Task toggleTaskStatus(String id, String userId) {
        Task task = findByIdAndUserId(id, userId);
        if (task.getStatus() == Task.Status.COMPLETED) {
            task.setStatus(Task.Status.PENDING);
        } else {
            task.setStatus(Task.Status.COMPLETED);
        }
        return taskRepository.save(task);
    }
    
    public List<Task> findOverdueTasksByUserId(String userId) {
        return taskRepository.findOverdueTasks(userId, LocalDateTime.now());
    }
    
    public List<Task> findTasksDueTodayByUserId(String userId) {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);
        return taskRepository.findByUserIdAndDueDateBetween(userId, startOfDay, endOfDay);
    }
    
    public List<Task> findByUserIdAndStatus(String userId, Task.Status status) {
        return taskRepository.findByUserIdAndStatus(userId, status);
    }
    
    public List<Task> findByUserIdAndCategory(String userId, String categoryId) {
        return taskRepository.findByUserIdAndCategoryId(userId, categoryId);
    }
    
    public List<Task> findByUserIdAndPriority(String userId, Task.Priority priority) {
        return taskRepository.findByUserIdAndPriority(userId, priority);
    }
}
