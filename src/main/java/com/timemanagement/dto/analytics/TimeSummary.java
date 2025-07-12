package com.timemanagement.dto.analytics;

import java.time.LocalDateTime;
import java.util.List;

public class TimeSummary {
    
    private Long totalTime; // in seconds
    private Period period;
    private List<TimeBreakdown> breakdown;
    
    public TimeSummary() {}
    
    public TimeSummary(Long totalTime, Period period, List<TimeBreakdown> breakdown) {
        this.totalTime = totalTime;
        this.period = period;
        this.breakdown = breakdown;
    }
    
    // Getters and setters
    public Long getTotalTime() {
        return totalTime;
    }
    
    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }
    
    public Period getPeriod() {
        return period;
    }
    
    public void setPeriod(Period period) {
        this.period = period;
    }
    
    public List<TimeBreakdown> getBreakdown() {
        return breakdown;
    }
    
    public void setBreakdown(List<TimeBreakdown> breakdown) {
        this.breakdown = breakdown;
    }
    
    public static class Period {
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        
        public Period() {}
        
        public Period(LocalDateTime startDate, LocalDateTime endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }
        
        public LocalDateTime getStartDate() {
            return startDate;
        }
        
        public void setStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
        }
        
        public LocalDateTime getEndDate() {
            return endDate;
        }
        
        public void setEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
        }
    }
    
    public static class TimeBreakdown {
        private String date;
        private Long timeSpent; // in seconds
        private Integer taskCount;
        
        public TimeBreakdown() {}
        
        public TimeBreakdown(String date, Long timeSpent, Integer taskCount) {
            this.date = date;
            this.timeSpent = timeSpent;
            this.taskCount = taskCount;
        }
        
        public String getDate() {
            return date;
        }
        
        public void setDate(String date) {
            this.date = date;
        }
        
        public Long getTimeSpent() {
            return timeSpent;
        }
        
        public void setTimeSpent(Long timeSpent) {
            this.timeSpent = timeSpent;
        }
        
        public Integer getTaskCount() {
            return taskCount;
        }
        
        public void setTaskCount(Integer taskCount) {
            this.taskCount = taskCount;
        }
    }
}
