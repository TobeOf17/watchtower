package com.watchtower.poller;

import com.watchtower.model.MetricEntry;
import com.watchtower.service.AlertService;
import com.watchtower.service.MetricService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class MetricPoller {

    private final MetricService metricService;
    private final AlertService alertService;
    private final RestTemplate restTemplate;
    private final ExecutorService pollExecutor = Executors.newSingleThreadExecutor();

    @Value("${watchtower.target.url}")
    private String targetUrl;

    @Value("${watchtower.alert.latency.threshold}")
    private long latencyThreshold;

    public MetricPoller(MetricService metricService, AlertService alertService) {
        this.metricService = metricService;
        this.alertService  = alertService;

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        this.restTemplate = new RestTemplate(factory);
    }

    @Scheduled(fixedRate = 10000)
    public void poll() {
        pollExecutor.submit(() -> {
            try {
                MetricEntry entry = new MetricEntry();
                entry.setServiceName("target-service");
                entry.setTimestamp(LocalDateTime.now());

                long start = System.currentTimeMillis();
                try {
                    ResponseEntity<String> response = restTemplate.getForEntity(targetUrl, String.class);
                    long latency = System.currentTimeMillis() - start;

                    entry.setLatencyMs(latency);
                    entry.setHttpStatus(response.getStatusCode().value());
                    entry.setIsSuccess(response.getStatusCode().is2xxSuccessful());
                    entry.setAvailability("UP");

                    if (latency > latencyThreshold) {
                        alertService.sendAlert(
                                "High Latency Detected",
                                "Service: target-service\nLatency: " + latency + "ms\n" +
                                        "Threshold: " + latencyThreshold + "ms"
                        );
                    }

                } catch (Exception e) {
                    long latency = System.currentTimeMillis() - start;
                    entry.setLatencyMs(latency);
                    entry.setHttpStatus(0);
                    entry.setIsSuccess(false);
                    entry.setAvailability("DOWN");

                    alertService.sendAlert(
                            "Service DOWN",
                            "Service: target-service is not responding.\n" +
                                    "Error: " + e.getMessage()
                    );
                }

                metricService.save(entry);

            } catch (Exception outer) {
                System.err.println("Poller thread error: " + outer.getMessage());
            }
        });
    }
}