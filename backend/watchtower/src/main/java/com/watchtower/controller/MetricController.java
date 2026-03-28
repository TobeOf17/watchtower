package com.watchtower.controller;

import com.watchtower.model.MetricEntry;
import com.watchtower.service.MetricService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class MetricController {

    private final MetricService metricService;
    private final RestTemplate restTemplate;

    public MetricController(MetricService metricService, RestTemplate restTemplate) {
        this.metricService = metricService;
        this.restTemplate  = restTemplate;
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

    @GetMapping("/saturation")
    public ResponseEntity<?> getSaturation() {
        try {
            return restTemplate.getForEntity(
                    "http://localhost:9090/health/stats", Object.class);
        } catch (Exception e) {
            return ResponseEntity.status(503).body("Saturation data unavailable");
        }
    }
}