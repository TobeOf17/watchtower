package com.watchtower.controller;

import com.watchtower.model.MetricEntry;
import com.watchtower.service.MetricService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class MetricController {

    private final MetricService metricService;

    public MetricController(MetricService metricService) {
        this.metricService = metricService;
    }

    @GetMapping("/metrics")
    public List<MetricEntry> getMetrics() {
        return metricService.getRecent();
    }

    @GetMapping("/summary")
    public Map<String, Object> getSummary() {
        return metricService.getSummary();
    }

    @GetMapping("/reliability")
    public Map<String, Object> getReliability() {
        return metricService.getReliabilityIndicators();
    }
}