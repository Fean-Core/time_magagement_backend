package com.timemanagement.repository;

import com.timemanagement.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    
    List<Task> findByUserId(String userId);
    
    List<Task> findByUserIdOrderByCreatedAtDesc(String userId);
    
    List<Task> findByUserIdAndStatus(String userId, Task.Status status);
    
    List<Task> findByUserIdAndCategoryId(String userId, String categoryId);
    
    List<Task> findByUserIdAndPriority(String userId, Task.Priority priority);
    
    @Query("{ 'userId': ?0, 'dueDate': { $gte: ?1, $lte: ?2 } }")
    List<Task> findByUserIdAndDueDateBetween(String userId, LocalDateTime start, LocalDateTime end);
    
    @Query("{ 'userId': ?0, 'status': { $ne: 'COMPLETED' }, 'dueDate': { $lt: ?1 } }")
    List<Task> findOverdueTasks(String userId, LocalDateTime now);
    
    @Query("{ 'userId': ?0, 'createdAt': { $gte: ?1, $lte: ?2 } }")
    List<Task> findByUserIdAndCreatedAtBetween(String userId, LocalDateTime start, LocalDateTime end);
}
