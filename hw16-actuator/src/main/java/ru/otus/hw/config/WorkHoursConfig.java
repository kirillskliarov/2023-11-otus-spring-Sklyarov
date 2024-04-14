package ru.otus.hw.config;

import java.time.LocalDateTime;

public interface WorkHoursConfig {
    LocalDateTime getStartOfDay();

    LocalDateTime getEndOfDay();
}
