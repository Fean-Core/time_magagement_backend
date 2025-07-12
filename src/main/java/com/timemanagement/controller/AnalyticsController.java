package com.timemanagement.controller;

import com.timemanagement.dto.analytics.ProductivityReport;
import com.timemanagement.dto.analytics.TimeSummary;
import com.timemanagement.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {
    
    @Autowired
    private AnalyticsService analyticsService;
    
    @GetMapping("/time-summary")
    public ResponseEntity<TimeSummary> getTimeSummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "day") String groupBy,
            Authentication auth) {
        
        String userEmail = auth.getName();
        TimeSummary timeSummary = analyticsService.getTimeSummary(userEmail, startDate, endDate, groupBy);
        return ResponseEntity.ok(timeSummary);
    }
    
    @GetMapping("/productivity")
    public ResponseEntity<ProductivityReport> getProductivityReport(
            @RequestParam(defaultValue = "month") String period,
            Authentication auth) {
        
        String userEmail = auth.getName();
        ProductivityReport report = analyticsService.getProductivityReport(userEmail, period);
        return ResponseEntity.ok(report);
    }
}
