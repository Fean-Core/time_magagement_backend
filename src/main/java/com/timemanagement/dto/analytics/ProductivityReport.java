package com.timemanagement.dto.analytics;

public class ProductivityReport {
    
    private String period;
    private Metrics metrics;
    private Trends trends;
    
    public ProductivityReport() {}
    
    public ProductivityReport(String period, Metrics metrics, Trends trends) {
        this.period = period;
        this.metrics = metrics;
        this.trends = trends;
    }
    
    // Getters and setters
    public String getPeriod() {
        return period;
    }
    
    public void setPeriod(String period) {
        this.period = period;
    }
    
    public Metrics getMetrics() {
        return metrics;
    }
    
    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }
    
    public Trends getTrends() {
        return trends;
    }
    
    public void setTrends(Trends trends) {
        this.trends = trends;
    }
    
    public static class Metrics {
        private Integer totalTasks;
        private Integer completedTasks;
        private Long totalTimeSpent; // in seconds
        private Long averageTaskDuration; // in seconds
        private Double productivityScore;
        
        public Metrics() {}
        
        public Metrics(Integer totalTasks, Integer completedTasks, Long totalTimeSpent, 
                      Long averageTaskDuration, Double productivityScore) {
            this.totalTasks = totalTasks;
            this.completedTasks = completedTasks;
            this.totalTimeSpent = totalTimeSpent;
            this.averageTaskDuration = averageTaskDuration;
            this.productivityScore = productivityScore;
        }
        
        public Integer getTotalTasks() {
            return totalTasks;
        }
        
        public void setTotalTasks(Integer totalTasks) {
            this.totalTasks = totalTasks;
        }
        
        public Integer getCompletedTasks() {
            return completedTasks;
        }
        
        public void setCompletedTasks(Integer completedTasks) {
            this.completedTasks = completedTasks;
        }
        
        public Long getTotalTimeSpent() {
            return totalTimeSpent;
        }
        
        public void setTotalTimeSpent(Long totalTimeSpent) {
            this.totalTimeSpent = totalTimeSpent;
        }
        
        public Long getAverageTaskDuration() {
            return averageTaskDuration;
        }
        
        public void setAverageTaskDuration(Long averageTaskDuration) {
            this.averageTaskDuration = averageTaskDuration;
        }
        
        public Double getProductivityScore() {
            return productivityScore;
        }
        
        public void setProductivityScore(Double productivityScore) {
            this.productivityScore = productivityScore;
        }
    }
    
    public static class Trends {
        private TrendData completionRate;
        private TrendData timeEfficiency;
        private TrendData taskCreationRate;
        
        public Trends() {}
        
        public Trends(TrendData completionRate, TrendData timeEfficiency, TrendData taskCreationRate) {
            this.completionRate = completionRate;
            this.timeEfficiency = timeEfficiency;
            this.taskCreationRate = taskCreationRate;
        }
        
        public TrendData getCompletionRate() {
            return completionRate;
        }
        
        public void setCompletionRate(TrendData completionRate) {
            this.completionRate = completionRate;
        }
        
        public TrendData getTimeEfficiency() {
            return timeEfficiency;
        }
        
        public void setTimeEfficiency(TrendData timeEfficiency) {
            this.timeEfficiency = timeEfficiency;
        }
        
        public TrendData getTaskCreationRate() {
            return taskCreationRate;
        }
        
        public void setTaskCreationRate(TrendData taskCreationRate) {
            this.taskCreationRate = taskCreationRate;
        }
    }
    
    public static class TrendData {
        private Double current;
        private Double previous;
        private Double change;
        
        public TrendData() {}
        
        public TrendData(Double current, Double previous, Double change) {
            this.current = current;
            this.previous = previous;
            this.change = change;
        }
        
        public Double getCurrent() {
            return current;
        }
        
        public void setCurrent(Double current) {
            this.current = current;
        }
        
        public Double getPrevious() {
            return previous;
        }
        
        public void setPrevious(Double previous) {
            this.previous = previous;
        }
        
        public Double getChange() {
            return change;
        }
        
        public void setChange(Double change) {
            this.change = change;
        }
    }
}
