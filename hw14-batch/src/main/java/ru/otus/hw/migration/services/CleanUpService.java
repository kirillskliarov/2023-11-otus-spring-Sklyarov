package ru.otus.hw.migration.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CleanUpService {

    @SuppressWarnings("unused")
    public void cleanUp() throws Exception {
        log.info("Выполняю завершающие мероприятия...");
        Thread.sleep(1000);
        log.info("Завершающие мероприятия закончены");
    }
}
