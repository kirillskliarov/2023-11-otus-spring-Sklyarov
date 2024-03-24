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
import ru.otus.hw.migration.data.GenreData;
import ru.otus.hw.migration.models.GenreMigration;
import ru.otus.hw.migration.repositories.GenreMigrationRepository;

import java.util.HashMap;

@Configuration
public class GenreStepConfig {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private GenreMigrationRepository genreMigrationRepository;

    @Autowired
    private MongoOperations template;

    @StepScope
    @Bean
    public MongoItemReader<GenreData> genreReader() {
        return new MongoItemReaderBuilder<GenreData>()
                .name("mongoItemReader")
                .template(template)
                .query(new Query())
                .targetType(GenreData.class)
                .sorts(new HashMap<String, Sort.Direction>(1))
                .collection("genres")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<GenreData, GenreMigration> genreProcessor() {
        return genreLegacy -> {
            GenreMigration genre = new GenreMigration();
            genre.setName(genreLegacy.getName());
            genre.setLegacyId(genreLegacy.get_id());
            return genre;
        };
    }

    @Bean(name = "transformGenres")
    public Step transformGenres(
            ItemReader<GenreData> genreReader,
            RepositoryItemWriter<GenreMigration> genreWriter,
            ItemProcessor<GenreData, GenreMigration> genreProcessor
    ) {
        return new StepBuilder("transformGenres", jobRepository)
                .<GenreData, GenreMigration>chunk(2, platformTransactionManager)
                .reader(genreReader)
                .processor(genreProcessor)
                .writer(genreWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<GenreMigration> genreWriter() {
        return new RepositoryItemWriterBuilder<GenreMigration>()
                .repository(genreMigrationRepository)
                .build();
    }
}
