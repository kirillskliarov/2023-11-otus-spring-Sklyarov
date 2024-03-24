package ru.otus.hw.legacy.initializing;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import ru.otus.hw.legacy.models.AuthorLegacy;
import ru.otus.hw.legacy.models.BookLegacy;
import ru.otus.hw.legacy.models.GenreLegacy;
import ru.otus.hw.legacy.services.AuthorLegacyService;
import ru.otus.hw.legacy.services.BookLegacyService;
import ru.otus.hw.legacy.services.GenreLegacyService;

/**
 * Нужен только чтобы замокать данные для переноса.
 * Это как бы наше старое приложение
 * В реальном прииложении этой инициализации не будет.
 */
@Component
@RequiredArgsConstructor
public class InitializingLegacyData implements InitializingBean {
    private final AuthorLegacyService authorLegacyService;

    private final GenreLegacyService genreLegacyService;

    private final BookLegacyService bookLegacyService;

    public void afterPropertiesSet() throws Exception {
        initLegacyData();
    }

    @SuppressWarnings("checkstyle:methodlength")
    private void initLegacyData() {
        // Authors
        AuthorLegacy pushkin = authorLegacyService.insert("Alexander Sergeyevich Pushkin");
        AuthorLegacy lermontov = authorLegacyService.insert("Mikhail Yuryevich Lermontov");
        AuthorLegacy shakespeare = authorLegacyService.insert("William Shakespeare");
        AuthorLegacy hawking = authorLegacyService.insert("Stephen Hawking");
        AuthorLegacy hemingway = authorLegacyService.insert("Ernest Miller Hemingway");
        AuthorLegacy wilde = authorLegacyService.insert("Oscar Fingal O'Fflahertie Wills Wilde");
        AuthorLegacy tolkien = authorLegacyService.insert("John Ronald Reuel Tolkien");

        // Genres
        GenreLegacy fiction = genreLegacyService.insert("fiction");
        GenreLegacy science = genreLegacyService.insert("science");
        GenreLegacy fantasy = genreLegacyService.insert("fantasy");

        // Books
        // Alexander Sergeyevich Pushkin
        BookLegacy ruslanAndLudmila = bookLegacyService.insert("Ruslan and Ludmila", pushkin.getId(), fantasy.getId());
        BookLegacy theCaptainsDaughter = bookLegacyService
                .insert("The Captain's Daughter", pushkin.getId(), fiction.getId());
        BookLegacy theTaleOfTheFishermanAndTheFish = bookLegacyService
                .insert("The Tale of the Fisherman and the Fish", fantasy.getId(), fiction.getId());

        // Mikhail Yuryevich Lermontov
        BookLegacy aHeroOfOurTime = bookLegacyService.insert("A Hero of Our Time", lermontov.getId(), fiction.getId());
        BookLegacy demon = bookLegacyService.insert("Demon", lermontov.getId(), fiction.getId());

        // William Shakespeare
        BookLegacy romeoAndJuliet = bookLegacyService.insert("Romeo and Juliet", shakespeare.getId(), fiction.getId());

        // Stephen Hawking
        BookLegacy aBriefHistoryOfTime = bookLegacyService
                .insert("A Brief History of Time", hawking.getId(), science.getId());
        BookLegacy theUniverseInANutshell = bookLegacyService
                .insert("The Universe in a Nutshell", hawking.getId(), science.getId());

        // Ernest Miller Hemingway
        BookLegacy aFarewellToArms = bookLegacyService.insert("A Farewell to Arms", hemingway.getId(), fiction.getId());

        // Oscar Fingal O'Fflahertie Wills Wilde
        BookLegacy thePictureOfDorianGray  = bookLegacyService
                .insert("The Picture of Dorian Gray ", wilde.getId(), fantasy.getId());

        // John Ronald Reuel Tolkien
        BookLegacy theLordOfTheRings  = bookLegacyService
                .insert("The Lord of the Rings", tolkien.getId(), fantasy.getId());
        BookLegacy hobbit  = bookLegacyService.insert("Hobbit", tolkien.getId(), fantasy.getId());

        System.out.println(authorLegacyService.findAll().stream().toList());
    }
}
