package ru.otus.hw.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.migration.services.CleanUpService;

@Configuration
public class JobConfig {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CleanUpService cleanUpService;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private JobCompletionListener jobCompletionListener;

    @Bean
    public Job importAuthosJob(
            Step transformAuthors,
            Step transformGenres,
            Step transformBooks
        ) {
        return new JobBuilder("importAuthorsJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(transformAuthors)
                .next(transformGenres)
                .next(transformBooks)
                .listener(jobCompletionListener)
                .build();
    }


}
