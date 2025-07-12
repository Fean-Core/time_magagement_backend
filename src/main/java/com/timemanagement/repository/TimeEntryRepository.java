package com.timemanagement.repository;

import com.timemanagement.model.TimeEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeEntryRepository extends MongoRepository<TimeEntry, String> {
    
    List<TimeEntry> findByUserId(String userId);
    
    List<TimeEntry> findByUserIdOrderByStartTimeDesc(String userId);
    
    List<TimeEntry> findByTaskId(String taskId);
    
    List<TimeEntry> findByUserIdAndTaskId(String userId, String taskId);
    
    @Query("{ 'userId': ?0, 'startTime': { $gte: ?1, $lte: ?2 } }")
    List<TimeEntry> findByUserIdAndStartTimeBetween(String userId, LocalDateTime start, LocalDateTime end);
    
    @Query("{ 'userId': ?0, 'endTime': null }")
    List<TimeEntry> findActiveTimeEntries(String userId);
    
    @Query("{ 'userId': ?0, 'endTime': null, 'taskId': ?1 }")
    List<TimeEntry> findActiveTimeEntriesByTask(String userId, String taskId);
}
