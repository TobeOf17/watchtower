package com.watchtower.simulator;

import com.sun.management.OperatingSystemMXBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class TargetSimulatorController {

    private static volatile String mode = "normal";

    @GetMapping
    public ResponseEntity<String> health() throws InterruptedException {
        switch (mode) {
            case "slow":
                Thread.sleep(4000);
                return ResponseEntity.ok("OK (slow)");
            case "down":
                return ResponseEntity.status(503).body("Service Unavailable");
            default:
                return ResponseEntity.ok("OK");
        }
    }

    @PostMapping("/mode")
    public ResponseEntity<String> setMode(@RequestParam String m) {
        mode = m;
        return ResponseEntity.ok("Mode set to: " + m);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> stats() {
        OperatingSystemMXBean os = (OperatingSystemMXBean)
                ManagementFactory.getOperatingSystemMXBean();

        double cpuLoad = os.getSystemCpuLoad() * 100;
        if (cpuLoad < 0) cpuLoad = 0;

        long totalMemory         = Runtime.getRuntime().totalMemory();
        long freeMemory          = Runtime.getRuntime().freeMemory();
        long usedMemory          = totalMemory - freeMemory;
        double memoryUsedPercent = (double) usedMemory / totalMemory * 100;

        Map<String, Object> stats = new HashMap<>();
        stats.put("cpuPercent",    Math.round(cpuLoad * 10.0) / 10.0);
        stats.put("memoryPercent", Math.round(memoryUsedPercent * 10.0) / 10.0);
        stats.put("memoryUsedMb",  usedMemory / (1024 * 1024));
        stats.put("memoryTotalMb", totalMemory / (1024 * 1024));
        return ResponseEntity.ok(stats);
    }
}