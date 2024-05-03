package ru.otus.hw.actuators;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.WorkHoursConfig;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class TimeHealthIndicator implements HealthIndicator {
    private final WorkHoursConfig workHoursConfig;

    @Override
    public Health health() {
        LocalDateTime startOfDay = workHoursConfig.getStartOfDay();
        LocalDateTime endOfDay = workHoursConfig.getEndOfDay();
        LocalDateTime now = LocalDateTime.now();

        boolean inService = now.isAfter(startOfDay) && now.isBefore(endOfDay);

        return inService
                ? Health.up().withDetail("message", "Работай, солнце ещё высоко!")
                    .build()
                : Health.down().status(Status.DOWN)
                    .withDetail("message", "Иди спать!")
                    .build();
    }
}
