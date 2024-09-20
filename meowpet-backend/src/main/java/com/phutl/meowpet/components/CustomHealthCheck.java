package com.phutl.meowpet.components;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.InetAddress; // Add this import statement

@Component
public class CustomHealthCheck implements HealthIndicator {

    @Override
    public Health health() {
        try {
            String computerName = InetAddress.getLocalHost().getHostName();
            return Health.up().withDetail("computerName", computerName).build(); // Trả về code 200
        } catch(Exception e) {
            return Health.down().withDetail("error", e.getMessage()).build();
        }
    }
    
}
