package com.watchtower.service;

import com.watchtower.model.MetricEntry;
import com.watchtower.repository.MetricRepository;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.*;

@Service
public class MetricService {

    private final MetricRepository repo;

    public MetricService(MetricRepository repo) {
        this.repo = repo;
    }

    public void save(MetricEntry entry) {
        repo.save(entry);
    }

    public List<MetricEntry> getRecent() {
        return repo.findTop100ByOrderByTimestampDesc();
    }

    public Map<String, Object> getSummary() {
        long total = repo.countTotal();
        long failures = repo.countFailures();
        double errorRate = total == 0 ? 0 : (double) failures / total * 100;

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalRequests", total);
        summary.put("failureCount", failures);
        summary.put("errorRatePercent", Math.round(errorRate * 100.0) / 100.0);
        return summary;
    }

    public Map<String, Object> getReliabilityIndicators() {
        List<MetricEntry> entries = repo.findAllByOrderByTimestampAsc();

        List<Long> detectionTimes = new ArrayList<>();
        List<Long> recoveryTimes  = new ArrayList<>();

        MetricEntry downStart  = null;
        MetricEntry firstFail  = null;
        MetricEntry lastUp     = null;

        for (MetricEntry entry : entries) {
            if (entry.getIsSuccess()) {
                // Service is UP
                if (downStart != null) {
                    // Recovery: time from first failure to now
                    long recoveryMs = Duration.between(downStart.getTimestamp(),
                            entry.getTimestamp()).toMillis();
                    recoveryTimes.add(recoveryMs);
                    downStart = null;
                    firstFail = null;
                }
                lastUp = entry;
            } else {
                // Service is DOWN
                if (downStart == null) {
                    downStart = entry;
                    // Detection: time from last successful poll to first failure
                    if (lastUp != null) {
                        long detectionMs = Duration.between(lastUp.getTimestamp(),
                                entry.getTimestamp()).toMillis();
                        detectionTimes.add(detectionMs);
                    }
                }
            }
        }

        double avgMttdSeconds = detectionTimes.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0) / 1000.0;

        double avgMttrSeconds = recoveryTimes.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0) / 1000.0;

        // Current incident duration if still down
        Long currentIncidentSeconds = null;
        if (downStart != null) {
            currentIncidentSeconds = Duration.between(downStart.getTimestamp(),
                    java.time.LocalDateTime.now()).toSeconds();
        }

        Map<String, Object> indicators = new HashMap<>();
        indicators.put("avgMttdSeconds",        Math.round(avgMttdSeconds));
        indicators.put("avgMttrSeconds",        Math.round(avgMttrSeconds));
        indicators.put("totalIncidents",        recoveryTimes.size());
        indicators.put("currentIncidentSeconds", currentIncidentSeconds);
        return indicators;
    }
}