package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {
    private final JobLauncher jobLauncher;

    private final Job importUserJob;

    @ShellMethod(value = "Migrate data", key = "migrate")
    public void runMigration() throws Exception {
        JobExecution execution = jobLauncher.run(importUserJob, new JobParametersBuilder().toJobParameters());
        System.out.println(execution);
    }

}
