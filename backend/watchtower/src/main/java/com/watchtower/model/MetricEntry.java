package com.watchtower.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "metric_entry")
public class MetricEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;
    private LocalDateTime timestamp;
    private Long latencyMs;
    private Integer httpStatus;
    private Boolean isSuccess;
    private String availability;

    // Getters and setters
    public Long getId() { return id; }
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public Long getLatencyMs() { return latencyMs; }
    public void setLatencyMs(Long latencyMs) { this.latencyMs = latencyMs; }
    public Integer getHttpStatus() { return httpStatus; }
    public void setHttpStatus(Integer httpStatus) { this.httpStatus = httpStatus; }
    public Boolean getIsSuccess() { return isSuccess; }
    public void setIsSuccess(Boolean isSuccess) { this.isSuccess = isSuccess; }
    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }
}