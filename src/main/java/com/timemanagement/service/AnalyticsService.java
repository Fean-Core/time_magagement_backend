package com.timemanagement.service;

import com.timemanagement.dto.analytics.ProductivityReport;
import com.timemanagement.dto.analytics.TimeSummary;
import com.timemanagement.model.Task;
import com.timemanagement.model.TimeEntry;
import com.timemanagement.repository.TaskRepository;
import com.timemanagement.repository.TimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {
    
    @Autowired
    private TimeEntryRepository timeEntryRepository;
    
    @Autowired
    private TaskRepository taskRepository;
    
    public TimeSummary getTimeSummary(String userId, LocalDateTime startDate, LocalDateTime endDate, String groupBy) {
        List<TimeEntry> timeEntries = timeEntryRepository.findByUserIdAndStartTimeBetween(userId, startDate, endDate);
        
        long totalTime = timeEntries.stream()
                .filter(entry -> entry.getDuration() != null)
                .mapToLong(TimeEntry::getDuration)
                .sum();
        
        TimeSummary.Period period = new TimeSummary.Period(startDate, endDate);
        
        List<TimeSummary.TimeBreakdown> breakdown = createTimeBreakdown(timeEntries, groupBy);
        
        return new TimeSummary(totalTime, period, breakdown);
    }
    
    public ProductivityReport getProductivityReport(String userId, String period) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = getStartDateForPeriod(now, period);
        
        List<Task> tasks = taskRepository.findByUserIdAndCreatedAtBetween(userId, startDate, now);
        List<TimeEntry> timeEntries = timeEntryRepository.findByUserIdAndStartTimeBetween(userId, startDate, now);
        
        int totalTasks = tasks.size();
        int completedTasks = (int) tasks.stream().filter(task -> task.getStatus() == Task.Status.COMPLETED).count();
        
        long totalTimeSpent = timeEntries.stream()
                .filter(entry -> entry.getDuration() != null)
                .mapToLong(TimeEntry::getDuration)
                .sum();
        
        long averageTaskDuration = totalTasks > 0 ? totalTimeSpent / totalTasks : 0;
        double productivityScore = totalTasks > 0 ? (double) completedTasks / totalTasks * 100 : 0;
        
        ProductivityReport.Metrics metrics = new ProductivityReport.Metrics(
                totalTasks, completedTasks, totalTimeSpent, averageTaskDuration, productivityScore);
        
        // For simplicity, using mock data for trends
        ProductivityReport.TrendData completionRateTrend = new ProductivityReport.TrendData(
                productivityScore, productivityScore * 0.9, 10.0);
        ProductivityReport.TrendData timeEfficiencyTrend = new ProductivityReport.TrendData(
                85.0, 80.0, 6.25);
        ProductivityReport.TrendData taskCreationRateTrend = new ProductivityReport.TrendData(
                1.2, 1.0, 20.0);
        
        ProductivityReport.Trends trends = new ProductivityReport.Trends(
                completionRateTrend, timeEfficiencyTrend, taskCreationRateTrend);
        
        return new ProductivityReport(period, metrics, trends);
    }
    
    private List<TimeSummary.TimeBreakdown> createTimeBreakdown(List<TimeEntry> timeEntries, String groupBy) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        Map<String, List<TimeEntry>> groupedEntries = timeEntries.stream()
                .filter(entry -> entry.getStartTime() != null)
                .collect(Collectors.groupingBy(entry -> entry.getStartTime().format(formatter)));
        
        List<TimeSummary.TimeBreakdown> breakdown = new ArrayList<>();
        
        for (Map.Entry<String, List<TimeEntry>> entry : groupedEntries.entrySet()) {
            String date = entry.getKey();
            List<TimeEntry> entries = entry.getValue();
            
            long timeSpent = entries.stream()
                    .filter(te -> te.getDuration() != null)
                    .mapToLong(TimeEntry::getDuration)
                    .sum();
            
            int taskCount = entries.size();
            
            breakdown.add(new TimeSummary.TimeBreakdown(date, timeSpent, taskCount));
        }
        
        return breakdown;
    }
    
    private LocalDateTime getStartDateForPeriod(LocalDateTime now, String period) {
        return switch (period.toLowerCase()) {
            case "week" -> now.minusWeeks(1);
            case "month" -> now.minusMonths(1);
            case "quarter" -> now.minusMonths(3);
            case "year" -> now.minusYears(1);
            default -> now.minusMonths(1); // default to month
        };
    }
}
