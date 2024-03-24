package ru.otus.hw.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.migration.data.AuthorData;
import ru.otus.hw.migration.models.AuthorMigration;
import ru.otus.hw.migration.repositories.AuthorMigrationRepository;

import java.util.HashMap;

@Configuration
public class AuthorStepConfig {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private AuthorMigrationRepository authorMigrationRepository;

    @Autowired
    private MongoOperations template;

    @StepScope
    @Bean
    public MongoItemReader<AuthorData> authorReader() {
        return new MongoItemReaderBuilder<AuthorData>()
                .name("mongoItemReader")
                .template(template)
                .query(new Query())
                .targetType(AuthorData.class)
                .sorts(new HashMap<String, Sort.Direction>(1))
                .collection("authors")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<AuthorData, AuthorMigration> authorProcessor() {
        return authorLegacy -> {
            AuthorMigration author = new AuthorMigration();
            author.setFullName(authorLegacy.getFullName());
            author.setLegacyId(authorLegacy.get_id());
            return author;
        };
    }

    @Bean(name = "transformAuthors")
    public Step transformAuthors(
            ItemReader<AuthorData> authorReader,
            RepositoryItemWriter<AuthorMigration> authorWriter,
            ItemProcessor<AuthorData, AuthorMigration> authorProcessor
    ) {
        return new StepBuilder("transformAuthors", jobRepository)
                .<AuthorData, AuthorMigration>chunk(2, platformTransactionManager)
                .reader(authorReader)
                .processor(authorProcessor)
                .writer(authorWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<AuthorMigration> authorWriter() {
        return new RepositoryItemWriterBuilder<AuthorMigration>()
                .repository(authorMigrationRepository)
                .build();
    }

}
