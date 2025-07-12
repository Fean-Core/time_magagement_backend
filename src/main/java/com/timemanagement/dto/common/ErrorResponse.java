package com.timemanagement.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {
    
    private String error;
    
    private String message;
    
    @JsonProperty("status_code")
    private int statusCode;
    
    private long timestamp;
    
    // Default constructor
    public ErrorResponse() {
        this.timestamp = System.currentTimeMillis();
    }
    
    // Constructor with parameters
    public ErrorResponse(String error, String message, int statusCode) {
        this.error = error;
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = System.currentTimeMillis();
    }
    
    // Static factory methods
    public static ErrorResponse badRequest(String message) {
        return new ErrorResponse("Bad Request", message, 400);
    }
    
    public static ErrorResponse unauthorized(String message) {
        return new ErrorResponse("Unauthorized", message, 401);
    }
    
    public static ErrorResponse forbidden(String message) {
        return new ErrorResponse("Forbidden", message, 403);
    }
    
    public static ErrorResponse notFound(String message) {
        return new ErrorResponse("Not Found", message, 404);
    }
    
    public static ErrorResponse conflict(String message) {
        return new ErrorResponse("Conflict", message, 409);
    }
    
    public static ErrorResponse unprocessableEntity(String message) {
        return new ErrorResponse("Unprocessable Entity", message, 422);
    }
    
    public static ErrorResponse internalServerError(String message) {
        return new ErrorResponse("Internal Server Error", message, 500);
    }
    
    // Getters and setters
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
