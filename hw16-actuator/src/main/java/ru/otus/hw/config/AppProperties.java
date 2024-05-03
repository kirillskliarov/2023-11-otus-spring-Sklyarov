package ru.otus.hw.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Setter
@ConfigurationProperties(prefix = "work-hours")
public class AppProperties implements WorkHoursConfig {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

    private String start;

    private String end;

    @Override
    public LocalDateTime getStartOfDay() {
        LocalTime startTime = LocalTime.parse(start, formatter);
        return LocalDate.now().atTime(startTime);
    }

    @Override
    public LocalDateTime getEndOfDay() {
        LocalTime endTime = LocalTime.parse(end, formatter);
        return LocalDate.now().atTime(endTime);
    }
}
