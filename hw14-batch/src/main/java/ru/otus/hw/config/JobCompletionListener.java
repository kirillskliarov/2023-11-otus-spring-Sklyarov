package ru.otus.hw.config;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionListener implements JobExecutionListener {

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("\n\n\n");
        System.out.println("Сказать админу БД, чтобы он дропнул колонки authors.legacy_id и genres_legacy_id");
        System.out.println("\n\n\n");
    }
}
