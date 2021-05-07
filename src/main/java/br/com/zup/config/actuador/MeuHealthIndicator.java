package br.com.zup.config.actuador;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MeuHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        Map<String, String> details = new HashMap<>();
        details.put("versão", "1.0.0");
        return Health.up().withDetails(details).build();
    }
}
