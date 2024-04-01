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
import ru.otus.hw.migration.data.BookData;
import ru.otus.hw.legacy.models.BookLegacy;
import ru.otus.hw.legacy.repositories.BookLegacyRepository;
import ru.otus.hw.migration.models.AuthorMigration;
import ru.otus.hw.migration.models.BookMigration;
import ru.otus.hw.migration.models.GenreMigration;
import ru.otus.hw.migration.repositories.AuthorMigrationRepository;
import ru.otus.hw.migration.repositories.BookMigrationRepository;
import ru.otus.hw.migration.repositories.GenreMigrationRepository;

import java.util.HashMap;

@Configuration
public class BookStepConfig {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private AuthorMigrationRepository authorMigrationRepository;

    @Autowired
    private GenreMigrationRepository genreMigrationRepository;

    @Autowired
    private BookMigrationRepository bookMigrationRepository;

    @Autowired
    private BookLegacyRepository bookLegacyRepository;

    @Autowired
    private MongoOperations template;

    @StepScope
    @Bean
    public MongoItemReader<BookData> bookReader() {
        return new MongoItemReaderBuilder<BookData>()
                .name("mongoItemReader")
                .template(template)
                .query(new Query())
                .targetType(BookData.class)
                .sorts(new HashMap<String, Sort.Direction>(1))
                .collection("books")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<BookData, BookMigration> bookProcessor() {
        return bookData -> {
            BookMigration book = new BookMigration();
            BookLegacy bookLegacy = bookLegacyRepository.findById(bookData.get_id()).orElseThrow();
            book.setTitle(bookData.getTitle());
            AuthorMigration author = authorMigrationRepository
                    .findByLegacyId(bookLegacy.getAuthor().getId()).orElseThrow();
            GenreMigration genre = genreMigrationRepository.findByLegacyId(bookLegacy.getGenre().getId()).orElseThrow();
            book.setAuthor(author);
            book.setGenre(genre);
            return book;
        };
    }

    @Bean(name = "transformBooks")
    public Step transformBooks(
            ItemReader<BookData> bookReader,
            RepositoryItemWriter<BookMigration> bookWriter,
            ItemProcessor<BookData, BookMigration> bookProcessor
    ) {
        return new StepBuilder("transformBooks", jobRepository)
                .<BookData, BookMigration>chunk(2, platformTransactionManager)
                .reader(bookReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<BookMigration> bookWriter() {
        return new RepositoryItemWriterBuilder<BookMigration>()
                .repository(bookMigrationRepository)
                .build();
    }

}
