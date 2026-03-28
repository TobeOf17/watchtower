package com.watchtower.simulator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@RestController
@RequestMapping("/health")
public class TargetSimulatorController {

    private static volatile String mode = "normal";
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

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
}